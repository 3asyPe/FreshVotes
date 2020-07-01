package com.freshvotes.contoller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.EmailService;
import com.freshvotes.service.FeatureService;
import com.freshvotes.service.UserService;

@Controller
@RequestMapping("/user/{userId}")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
		
	@Autowired
	private FeatureService featureService;
	
	private Map<String, Integer> statuses = new HashMap<String, Integer>(){
		{
			put("Accepted", 4);
			put("Review", 3);
			put("Pending review", 2);
			put("Rejected", 1);
		}
	};
	
	@GetMapping("/verification")
	public String activateAccount(@PathVariable int userId,
								  @RequestParam(required = true) String key,
								  Model model) {
		User user = userService.findById(userId);
		Boolean result = emailService.varifyKey(user, key);
		
		if(result == true) {
			userService.activateUser(user);
			emailService.deleteByEmailAddress(user.getUsername());
			userService.authorizeUser(user);
			return "redirect:/user/" + userId + "/profile/edit";
		}
		else {
			return "redirect:/login?notactivated";
		}
		
	}
	
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
		
		return "redirect:/user/" + user.getId() + "/profile";
	}
	
	@PostMapping("/profile/pictureUpload")
	public String saveUserImage(@RequestParam MultipartFile  image,
								@PathVariable int userId,
								@AuthenticationPrincipal User securityUser) throws IOException {
		User user = userService.findById(userId);
		
		userService.uploadUserImage(image, user, securityUser);
		
		return "redirect:/user/" + userId + "/profile";
	}
	
	@GetMapping("/features")
	public String showUserFeatureRequests(@PathVariable int userId,
										  @RequestParam(required = false) String search,
										  Model model) {
		User user = userService.findById(userId);
		List<Feature> features;
		
		if(search == null) {
			features = featureService.findByUser(user);
		}
		else {
			features = featureService.findByUserAndTitleContaining(user, search);
		}
		
		if(features != null) {
			features.sort((Feature f1, Feature f2) -> {
				int statusesResult = statuses.get(f2.getStatus()) - statuses.get(f1.getStatus());
				if(statusesResult != 0) {
					return statusesResult;
				}
				
				return f2.countVotes() - f1.countVotes();
			}); 
		}
		
		model.addAttribute("features", features);
		
		return "userFeatureRequests";
	}
	
}
