package com.freshvotes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.repository.FeatureRepository;
import com.freshvotes.repository.ProductRepository;

@Service
public class FeatureService {

	@Autowired 
	private ProductRepository productRepo;
	
	@Autowired
	private FeatureRepository featureRepo;
	
	public Feature createFeature(int productId) {
		Feature feature = new Feature();
		
		Optional<Product> productOpt = productRepo.findById(productId);
		
		if(productOpt.isPresent()) {
			Product product = productOpt.get();
			feature.setProduct(product);
			feature.setStatus("Pending review");
			product.getFeatures().add(feature);
			
			feature = featureRepo.save(feature);
		}
		else {
			throw new RuntimeException("Can not get a product with id - " + productId);
		}
		
		return feature;
	}
}
