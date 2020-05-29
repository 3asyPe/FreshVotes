package com.freshvotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.domain.VoteId;

public interface VoteRepository extends JpaRepository<Vote, VoteId>{
	
	@Query("select count(v) from Vote v "
		 + "left join v.primaryKey pk "
		 + "where v.upvote = ?1 and pk.feature = ?2 ")
	public Integer countVotes(boolean upvote, Feature feature);
	
	@Query("select v.upvote from Vote v "
			 + "left join v.primaryKey pk "
			 + "where pk.user = ?1 and pk.feature = ?2 ")
	public Boolean findByUserAndFeature(User user, Feature feature);

}
