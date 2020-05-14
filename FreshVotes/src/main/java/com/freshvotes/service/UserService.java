package com.freshvotes.service;

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
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUser(user);
		
		user.getAuthorities().add(authority);
		return userRepo.save(user);
	}
}
