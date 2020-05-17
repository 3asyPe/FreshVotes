package com.freshvotes.contoller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.ProductRepository;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@GetMapping("/products/{productId}")
	public String getProduct(@PathVariable int productId,
							 Model model, HttpServletResponse response) throws IOException {
		Optional<Product> productOpt = productRepo.findById(productId);
		
		if(productOpt.isPresent()) {
			Product product = productOpt.get();
			model.addAttribute("product", product);
		}
		else {
			response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with id " + productId);
		}
		
		return "product";
	}
	
	@PostMapping("/products/{productId}")
	public String saveProduct(Product product, @PathVariable int productId) {
		System.out.println(product);
		productRepo.save(product);
		return "redirect:/dashboard";
	}
	
}
