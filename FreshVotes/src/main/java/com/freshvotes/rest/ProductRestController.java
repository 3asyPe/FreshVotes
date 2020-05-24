package com.freshvotes.rest;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freshvotes.domain.Product;
import com.freshvotes.repository.ProductRepository;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

	@Autowired
	private ProductRepository productRepo;
	
	@PostMapping("/name/match")
	public boolean checkNameMatching(@RequestBody Object obj) {
		Map<String, String> json = (Map<String, String>) obj;
		Optional<Product> result = productRepo.findByName(json.get("name"));
		
		if (result.isPresent()) {
			return false;
		}
		
		return true;
	}
}
