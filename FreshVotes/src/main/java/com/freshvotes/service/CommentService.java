package com.freshvotes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	public Comment findById(Integer id) {
		Optional<Comment> commentOpt = commentRepo.findById(id);
		Comment comment = null;
		
		if(commentOpt.isPresent()) {
			comment = commentOpt.get();
		}
		else {
			throw new RuntimeException("There is no feature with id - " + id);
		}
		return comment;
	}
	
	public List<Comment> findByFeatureId(Integer featureId) {
		return commentRepo.findByFeatureId(featureId);
	}
	
	public List<Comment> findByIdAndComment(Integer featureId, Comment comment){
		return commentRepo.findByFeatureIdAndComment(featureId, comment);
	}
	
	public List<Comment> findByFeatureIdAndComment(Integer featureId, Comment comment){
		return commentRepo.findByFeatureIdAndComment(featureId, comment);
	}
	
	public Comment save(Comment comment) {
		return commentRepo.save(comment);
	}
}
