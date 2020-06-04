package com.freshvotes.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;
import com.freshvotes.service.UserService;

@Controller
@RequestMapping("/user/{userId}")
public class UserController {

	@Autowired
	private UserService userService;
		
	@Autowired
	private FeatureService featureService;
	
	@GetMapping("/profile")
	public String showUserProfile(@PathVariable int userId,
								  Model model) {
		User user = userService.findById(userId);
		
		model.addAttribute("user", user);
		
		return "userProfile";
	}
	
	@GetMapping("/profile/edit")
	public String editUserProfile(@PathVariable int userId,
								  Model model) {
		User user = userService.findById(userId);
		
		model.addAttribute("user", user);
		
		return "editUserProfile";
	}
	
	@PostMapping("/profile/edit")
	public String saveUserProfile(User user) {
		userService.save(user);
		
		return "userProfile";
	}
	
	@GetMapping("/features")
	public String showUserFeatureRequests(@PathVariable int userId,
										  Model model) {
		User user = userService.findById(userId);
		List<Feature> features = featureService.findByUser(user);
		
		model.addAttribute("features", features);
		
		return "userFeatureRequests";
	}
	
}
