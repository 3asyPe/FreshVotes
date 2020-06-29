package com.freshvotes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freshvotes.domain.VarifyEmail;
import com.freshvotes.domain.User;
import com.freshvotes.repository.EmailRepository;

@Service
public class EmailService{
	
	@Autowired
	private EmailRepository emailRepo;
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendAuthenticationEmail(User user){
		try {
			Integer keyLength = 40;
			String key = RandomStringUtils.randomAlphanumeric(keyLength);
			String url = "http://localhost:8080/user/" + user.getId() + "/verification?key=" + key;
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
			
			VarifyEmail email = new VarifyEmail();
			email.setEmailAddress(user.getUsername());
			email.setKey(key);
			email.setCreatedDate(new Date());
			
			emailRepo.save(email);
		}
		catch (MessagingException exc) {
			exc.printStackTrace();
		}
	}
	
	public Boolean varifyKey(User user, String key){
		List<VarifyEmail> emails = emailRepo.findByEmailAddress(user.getUsername());
		
		if(emails == null || emails.isEmpty()) {
			return false;
		}
			
		long prevDay = System.currentTimeMillis() - 1000*60*60*24;
		Date dayBefore = new Date(prevDay);
	
		for(VarifyEmail email : emails) {
			Date testDate = email.getCreatedDate();
			if(testDate.before(dayBefore)){
				emailRepo.delete(email);
				if(email.getVarifyKey().equals(key)) {
					return null;
				}
			}
			else if(email.getVarifyKey().equals(key)){
				return true;
			}
		}
		
		return false;
	}

	@Transactional
	public void deleteByEmailAddress(String emailAddress) {
		emailRepo.deleteByEmailAddress(emailAddress);
	}
	
}
