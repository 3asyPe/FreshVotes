package com.freshvotes.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Comment {

	@EmbeddedId
	private CommentId primaryKey;
	
	@Column(length=5000)
	private String text;

	public CommentId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(CommentId primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
