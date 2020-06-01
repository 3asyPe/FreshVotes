package com.freshvotes.contoller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.freshvotes.service.CommentService;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FeatureService featureService;
		
	@GetMapping("/comments")
	@ResponseBody
	public List<Comment> getComments(@PathVariable int featureId){
		return commentService.findByFeatureId(featureId);
	}
	
	@PostMapping("/comments")
	@ResponseBody
	public Integer createComment(@AuthenticationPrincipal User user,
							     @RequestBody Object obj,
							     @PathVariable int featureId) throws JsonProcessingException, ParseException {
		Comment comment = new Comment();

		Date date = new Date();
		
		Map<String, String> json = (Map<String, String>)obj;
		String text = json.get("text");
		String commentId = json.get("commentId");
		
		if(commentId != null) {
			comment.setComment(commentService.findById(Integer.parseInt(commentId)));
		}
		
		comment.setText(text);
		comment.setFeature(featureService.findById(featureId));
		comment.setUser(user);
		comment.setCreatedDate(date);
		
		return commentService.save(comment).getId();
		
	}
}
