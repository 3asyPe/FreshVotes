package com.freshvotes.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freshvotes.domain.User;
import com.freshvotes.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/username/match")
	public boolean checkTheUsername(@RequestBody Username username) {
		User result = userRepo.findByUsername(username.getUsername());
		
		if(result == null) {
			return true;
		}
		
		return false;
	}
}
