package com.freshvotes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.User;
import com.freshvotes.repository.UserRepository;
import com.freshvotes.security.Authority;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public User createUser(User user) {
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		user.setImageURL("/images/User_Placeholder.jpg");
		
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUser(user);
		
		user.getAuthorities().add(authority);
		return userRepo.save(user);
	}
	
	public User findById(Integer userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		User user = null;
		
		if(userOpt.isPresent()) {
			user = userOpt.get();
		}
		else {
			throw new RuntimeException("There is no user with id - " + userId);
		}
		
		return user;
	}
}
