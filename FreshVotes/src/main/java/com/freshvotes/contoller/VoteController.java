package com.freshvotes.contoller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.domain.VoteId;
import com.freshvotes.repository.VoteRepository;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}")
public class VoteController {

	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@PostMapping("/vote")
	@ResponseBody
	public void doVote(@RequestBody Object obj,
					   @AuthenticationPrincipal User user,
					   @PathVariable int featureId) {
		Vote vote = new Vote();
		VoteId voteId = new VoteId();
		
		Feature feature = featureService.findById(featureId);
		Map<String, Boolean> json = (Map<String, Boolean>)obj;
		Boolean upvote = json.get("upvote");
		
		vote.setUpvote(upvote);
		vote.setPrimaryKey(voteId);
		voteId.setUser(user);
		voteId.setFeature(feature);
		
		voteRepository.save(vote);
	}
}
