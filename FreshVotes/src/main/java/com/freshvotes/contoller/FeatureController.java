package com.freshvotes.contoller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.repository.VoteRepository;
import com.freshvotes.service.CommentService;
import com.freshvotes.service.FeatureService;
import com.sun.istack.Nullable;

@Controller
@RequestMapping("/products/{productId}")
public class FeatureController {
	
	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VoteRepository voteRepo;
	
	@GetMapping("/createFeature")
	public String showCreateFeature(Model model,
									@PathVariable int productId) {
		Feature feature = new Feature();
		model.addAttribute("feature", feature);
		model.addAttribute("productId", productId);
		return "feature";
	}
	
	@PostMapping("/createFeature")
	public String createFeatures(@AuthenticationPrincipal User user,
								 @PathVariable int productId,
								 HttpServletResponse response,
								 Feature feature) throws IOException {
		try {
			feature = featureService.createFeature(feature, productId, user);
			return "redirect:/products/" + productId + "/features/" + feature.getId();
		}
		catch(Exception exc) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with id " + productId);
		}
			
		return "redirect:/";
	}
	
	@GetMapping("/features/{featureId}")
	public String getFeature(@AuthenticationPrincipal User user,
							 @PathVariable int productId,
							 @PathVariable int featureId,
							 HttpServletResponse response,
							 Model model) throws IOException {
		try {
			Feature feature = featureService.findById(featureId);
			Integer upvotes = voteRepo.countVotes(true, feature);
			Integer downvotes = voteRepo.countVotes(false, feature);
			
			@Nullable
			Boolean isUpvoted = voteRepo.findByUserAndFeature(user, feature);
			
			boolean like = false;
			boolean dislike = false;
			
			// NullPointerException
			if(isUpvoted == null) {
				
			}
			else if(isUpvoted == true) {
				like = true;
			}
			else {
				dislike = true;
			}
			
			model.addAttribute("feature", feature);
			model.addAttribute("user", user);	
			model.addAttribute("comments", commentService.findByIdAndComment(featureId, null));
			model.addAttribute("upvotes", upvotes);
			model.addAttribute("downvotes", downvotes);
			model.addAttribute("like", like);
			model.addAttribute("dislike", dislike);
			
		}
		catch(Exception exc) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with id " + productId);
		}
		return "featureUserView";
	}
	
	@PostMapping("/features/{featureId}")
	public String updateFeature(@PathVariable int productId,
								@PathVariable int featureId,
								Feature feature) throws UnsupportedEncodingException {
		feature = featureService.save(feature);
		return "redirect:/products/" + URLEncoder.encode(feature.getProduct().getName(),
												  "UTF-8");
	}
	
	@PostMapping("/features/{featureId}/delete")
	public String deleteStatus(@PathVariable int featureId,
							   @PathVariable int productId,
							   @AuthenticationPrincipal User user) {
		
		featureService.deleteById(featureId);
		
		return "redirect:/user/" + user.getId() + "/features";
	}
	
	@PostMapping("/features/{featureId}/status")
	@ResponseBody
	public void changeFeatureStatus(@RequestBody Object obj,
									  @PathVariable int featureId,
									  @PathVariable int productId) {
		
		Map<String, String> json = (Map<String, String>)obj;
		
		Feature feature = featureService.findById(featureId);
		feature.setStatus(json.get("status"));
		featureService.save(feature);
	}
}






