package com.freshvotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Comment;
import com.freshvotes.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	public List<Comment> findByIdAndComment(Integer featureId, Comment comment){
		return commentRepo.findByFeatureIdAndComment(featureId, comment);
	}
}
