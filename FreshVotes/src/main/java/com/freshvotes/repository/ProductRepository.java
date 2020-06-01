package com.freshvotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	public List<Product> findByUser(User user);
	
	public Optional<Product> findByName(String name);
	
	public List<Product> findByPublished(boolean published);

	public List<Product> findByUserAndNameContaining(User user, String name);

	public List<Product> findByPublishedAndNameContaining(boolean b, String search);
}
