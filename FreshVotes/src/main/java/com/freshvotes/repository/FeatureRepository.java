package com.freshvotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Integer>{
	
}
