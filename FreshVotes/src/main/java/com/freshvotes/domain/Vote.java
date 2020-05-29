package com.freshvotes.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Vote {

	@EmbeddedId
	private VoteId primaryKey;
	
	@Column(nullable = true)
	private Boolean upvote;

	public Boolean isUpvote() {
		return upvote;
	}

	public void setUpvote(Boolean upvote) {
		this.upvote = upvote;
	}

	public VoteId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(VoteId primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}
