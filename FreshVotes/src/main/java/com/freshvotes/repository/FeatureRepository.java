package com.freshvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;

public interface FeatureRepository extends JpaRepository<Feature, Integer>{

	List<Feature> findByProduct(Product product);

	void deleteByTitle(String title);
	
}
