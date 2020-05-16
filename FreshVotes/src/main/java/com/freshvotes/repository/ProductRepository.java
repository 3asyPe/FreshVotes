package com.freshvotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
