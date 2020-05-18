package com.freshvotes.contoller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {
	
	@Autowired
	private FeatureService featureService;
	
	@PostMapping("")
	public String createFeatures(@PathVariable int productId,
								 HttpServletResponse response) throws IOException {
		try {
			Feature feature = featureService.createFeature(productId);
			return "redirect:/products/" + productId + "/features/" + feature.getId();
		}
		catch(Exception exc) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with id " + productId);
		}
			
		return "redirect:/";
	}
	
	@GetMapping("/{featureId}")
	public String getFeature(@PathVariable int productId,
							 @PathVariable int featureId) {
		return "feature";
	}
}
