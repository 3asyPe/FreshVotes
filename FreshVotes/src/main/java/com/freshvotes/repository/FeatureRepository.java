package com.freshvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

public interface FeatureRepository extends JpaRepository<Feature, Integer>{

	List<Feature> findByProduct(Product product);

	void deleteByTitle(String title);

	List<Feature> findByUser(User user);

	List<Feature> findByProductAndStatusIn(Product product, String[] status);

	List<Feature> findByUserAndTitleContaining(User user, String name);
	
}
