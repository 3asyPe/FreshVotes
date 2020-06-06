package com.freshvotes.contoller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import com.freshvotes.service.FeatureService;
import com.freshvotes.service.UserService;

@Controller
@RequestMapping("/user/{userId}")
public class UserController {

	@Autowired
	private UserService userService;
		
	@Autowired
	private FeatureService featureService;
	
	@Autowired
    private HttpServletRequest request;
	
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

		File file = new File(new ClassPathResource("static/images/").getFile().toString() + "/" + image.getOriginalFilename().trim());
		image.transferTo(file);
		
		User user = userService.findById(userId);
		String imageURL = "/images/" + image.getOriginalFilename().trim();
		
		user.setImageURL(imageURL);
		securityUser.setImageURL(imageURL);
		
		userService.save(user);
		
		return "redirect:/user/" + userId + "/profile";
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
