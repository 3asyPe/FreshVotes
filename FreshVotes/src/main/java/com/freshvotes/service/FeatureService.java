package com.freshvotes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.FeatureRepository;
import com.freshvotes.repository.ProductRepository;

@Service
public class FeatureService {

	@Autowired 
	private ProductRepository productRepo;
	
	@Autowired
	private FeatureRepository featureRepo;
	
	public Feature createFeature(int productId, User user) {
		Feature feature = new Feature();
		
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
}
