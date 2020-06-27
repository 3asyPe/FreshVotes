package com.freshvotes.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.User;

@Service
public class EmailService{
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendAuthenticationEmail(User user) throws MessagingException {
		String encodedUsername = DigestUtils.sha256Hex(user.getUsername());
		String url = "http://localhost:8080/user/" + user.getId() + "/verification/" + encodedUsername;
		Boolean multipart = true;
		
		String htmlMsg = "<h1 style=\"text-align: center\">Please activate your account</h1>" + 
						 "<h2>Dear " + user.getName() + ",</h2>" + 
						 "<p>To complete the registration process, please confirm your email by following this link:</p>" + 
						 "<p><a href= '" +  url + "'>Confirmation link</a></p>" + 
						 "<div>Thank you!</div>";
		
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
		
		message.setContent(htmlMsg, "text/html");
		helper.setTo(user.getUsername());
		helper.setSubject("FreshVotes account activation");
		
		emailSender.send(message);
	}
	
	
}
