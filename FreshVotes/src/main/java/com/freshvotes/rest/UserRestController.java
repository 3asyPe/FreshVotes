package com.freshvotes.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freshvotes.domain.User;
import com.freshvotes.repository.UserRepository;
import com.freshvotes.service.EmailService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/username/match")
	public boolean checkUsernameMatching(@RequestBody Object obj) {
		Map<String, String> json = (Map<String, String>)obj;
		User user = userRepo.findByUsername(json.get("username"));
		
		if(user == null) {
			return true;
		}
		
		if(!emailService.varifyEmailExists(user.getUsername())) {
			emailService.sendAuthenticationEmail(user);
		}
		
		return false;
	}
	
	@PostMapping("/user/loggedin")
	public boolean isLoggedIn(@AuthenticationPrincipal User user,
							 @RequestBody Object obj) {
		Map<String, String> json = (Map<String, String>)obj;

		if(user == null || user.getUsername().equals(json.get("username"))) {
			return false;
		}
		
		return true;
	}
}
