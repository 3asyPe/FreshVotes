package com.freshvotes.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freshvotes.domain.Comment;
import com.freshvotes.repository.CommentRepository;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}/comments")
public class CommentController {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@GetMapping("")
	@ResponseBody
	public List<Comment> getComments(@PathVariable int featureId){
		return commentRepo.findByFeatureId(featureId);
	}
	

}
