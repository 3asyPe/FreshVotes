package com.freshvotes.contoller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {
	
	@Autowired
	private FeatureService featureService;
	
	@PostMapping("")
	public String createFeatures(@AuthenticationPrincipal User user,
								 @PathVariable int productId,
								 HttpServletResponse response) throws IOException {
		try {
			Feature feature = featureService.createFeature(productId, user);
			return "redirect:/products/" + productId + "/features/" + feature.getId();
		}
		catch(Exception exc) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with id " + productId);
		}
			
		return "redirect:/";
	}
	
	@GetMapping("/{featureId}")
	public String getFeature(@AuthenticationPrincipal User user,
							 @PathVariable int productId,
							 @PathVariable int featureId,
							 HttpServletResponse response,
							 Model model) throws IOException {
		try {
			Feature feature = featureService.findById(featureId);
			model.addAttribute("feature", feature);
			model.addAttribute("user", user);	
			model.addAttribute("comments", feature.getComments());
		}
		catch(Exception exc) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with id " + productId);
		}
		return "feature";
	}
	
	@PostMapping("/{featureId}")
	public String updateFeature(@PathVariable int productId,
								@PathVariable int featureId,
								Feature feature) throws UnsupportedEncodingException {
		feature = featureService.save(feature);
		return "redirect:/p/" + URLEncoder.encode(feature.getProduct().getName(),
												  "UTF-8");
	}
}






