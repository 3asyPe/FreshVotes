package com.freshvotes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.repository.FeatureRepository;
import com.freshvotes.repository.ProductRepository;

@Service
public class FeatureService {

	@Autowired 
	private ProductRepository productRepo;
	
	@Autowired
	private FeatureRepository featureRepo;
	
	public Feature createFeature(Feature feature, int productId, User user) {	
		Optional<Product> productOpt = productRepo.findById(productId);
		
		if(productOpt.isPresent()) {
			Product product = productOpt.get();
			feature.setProduct(product);
			feature.setStatus("Pending review");
			product.getFeatures().add(feature);
			feature.setUser(user);
			user.getFeatures().add(feature);
			
			feature = featureRepo.save(feature);
		}
		else {
			throw new RuntimeException("Can not get a product with id - " + productId);
		}
		
		return feature;
	}

	public Feature save(Feature feature) {
		return featureRepo.save(feature);
	}

	public Feature findById(int featureId) {
		Optional<Feature> featureOpt = featureRepo.findById(featureId);
		Feature feature = null;
		
		if(featureOpt.isPresent()) {
			feature = featureOpt.get();
		}
		else {
			throw new RuntimeException("There is no feature with id - " + featureId);
		}
		return feature;
	}

	public List<Feature> findByProduct(Product product) {
		return featureRepo.findByProduct(product);
	}

	public void doVote(Feature feature, Boolean upvote, Boolean previousUpvote) {
		if(upvote == null) {
			if(previousUpvote == null) {
				throw new RuntimeException("upvote and previousUpvote are both null");
			}
			else if(previousUpvote == false){
				feature.removeDownvote();
			}
			else {
				feature.removeUpvote();
			}
		}
		else if(upvote == true) {
			feature.addUpvote();
			
			// NullException
			if(previousUpvote == null) {
				
			}
			else if(previousUpvote == false) {
				feature.removeDownvote();
			}
			else if(previousUpvote == true) {
				throw new RuntimeException("upvote and previousUpvote are both true");
			}
		}
		else {
			feature.addDownvote();
			
			// NullException
			if(previousUpvote == null) {
				
			}
			else if(previousUpvote == true) {
				feature.removeUpvote();
			}
			else if(previousUpvote == false) {
				throw new RuntimeException("upvote and previousUpvote are both false");
			}
		}
		save(feature);
	}
}
