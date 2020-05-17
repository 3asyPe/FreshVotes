package com.freshvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public List<Product> findByUser(User user);
}