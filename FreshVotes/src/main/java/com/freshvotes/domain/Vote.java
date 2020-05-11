package com.freshvotes.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Vote {

	@EmbeddedId
	private VoteId primaryKey;
	
	private boolean upvote;

	public boolean isUpvote() {
		return upvote;
	}

	public void setUpvote(boolean upvote) {
		this.upvote = upvote;
	}

	public VoteId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(VoteId primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}
