package com.freshvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	List<Comment> findByFeatureId(int featureId);

	List<Comment> findByFeatureIdAndComment(Integer featureId, Comment comment);

}
