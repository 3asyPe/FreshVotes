package com.freshvotes.contoller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshvotes.domain.Comment;
import com.freshvotes.domain.User;
import com.freshvotes.repository.CommentRepository;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}")
public class CommentController {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@GetMapping("/comments")
	@ResponseBody
	public List<Comment> getComments(@PathVariable int featureId){
		return commentRepo.findByFeatureId(featureId);
	}
	
	@PostMapping("/comments")
	@ResponseBody
	public String createComment(@AuthenticationPrincipal User user,
							    @RequestBody Comment comment,
							    @PathVariable int featureId) throws JsonProcessingException {
		Date date = new Date();  


		comment.setFeature(featureService.findById(featureId));
		comment.setUser(user);
		comment.setCreatedDate(date);
		
		return objectMapper.writeValueAsString(commentRepo.save(comment));
		
	}
}
