package com.freshvotes.service;

import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freshvotes.domain.User;
import com.freshvotes.repository.AuthorityRepository;
import com.freshvotes.repository.UserRepository;
import com.freshvotes.security.Authority;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthorityRepository authorityRepo;
	
	@Autowired
	private PasswordEncoder encoder;	

	@Autowired
	private EmailService emailService;
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public User createUser(User user) throws MessagingException {
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		user.setImageURL("/images/User_Placeholder.jpg");
		
		Authority authority = new Authority();
		authority.setAuthority("ROLE_UNABLE");
		authority.setUser(user);
		
		user.getAuthorities().add(authority);
		
		user = userRepo.save(user);
		
		emailService.sendAuthenticationEmail(user);
		
		return user;
	}
	
	@Transactional
	public User activateUser(User user) {
		Optional<Authority> authorityOpt = authorityRepo.findByUserAndAuthority(user, "ROLE_UNABLE");
		
		if(authorityOpt.isPresent()) {
			Authority auth = authorityOpt.get();
			user.getAuthorities().remove(auth);
			authorityRepo.delete(auth);
		}
		
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
