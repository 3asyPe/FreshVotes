package com.freshvotes.contoller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freshvotes.domain.User;
import com.freshvotes.repository.UserRepository;
import com.freshvotes.service.EmailService;

@Controller
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/verifyEmail")
	public String verifyEmailForm() {
		return "verifyEmail";
	}
	
	@PostMapping("/verifyEmail")
	@ResponseBody
	public Boolean verifyEmail(@RequestBody Object obj) {
		Map<String, String> json = (Map<String, String>) obj;
		String email = json.get("email");
		User user = userRepo.findByUsername(email);
		
		if(user == null) {
			return false;
		}
		
		emailService.sendAuthenticationEmail(user);
		
		return true;
	}
}
