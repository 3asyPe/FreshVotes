package com.freshvotes.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	
	private String defaultUserImageUrl = "/images/User_Placeholder.jpg";
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public User createUser(User user) throws MessagingException {
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		user.setImageURL(defaultUserImageUrl);
		
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
	
	public void authorizeUser(User user) {
		List<Authority> authorities = authorityRepo.findByUser(user);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public void uploadUserImage(MultipartFile  image,
							User user,
							User securityUser) throws IOException{
		
		String defaultPath = new ClassPathResource("static/images/").getFile().toString();
		String randomName = RandomStringUtils.randomAlphanumeric(6);
		
		File file = new File(defaultPath + "/" + randomName);
		image.transferTo(file);
		String imageURL = "/images/" + randomName;
		
		deleteUserImage(user);
		
		user.setImageURL(imageURL);
		securityUser.setImageURL(imageURL);
		
		userRepo.save(user);
	}
	
	public void deleteUserImage(User user) throws IOException {
		if(!user.getImageURL().equals(defaultUserImageUrl)) {	
			String defaultPath = new ClassPathResource("static").getFile().toString();
			File previousImage = new File(defaultPath + "/" + user.getImageURL());
			System.out.println(previousImage.exists());
			try
	        { 
	            System.out.println(Files.deleteIfExists(Paths.get(previousImage.getAbsolutePath()))); 
	        } 
	        catch(NoSuchFileException e) 
	        { 
	            System.out.println("No such file/directory exists"); 
	        } 
	        catch(DirectoryNotEmptyException e) 
	        { 
	            System.out.println("Directory is not empty."); 
	        } 
	        catch(IOException e) 
	        { 
	            System.out.println("Invalid permissions."); 
	        } 
	          
	        System.out.println("Deletion successful."); 
			
		}
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
