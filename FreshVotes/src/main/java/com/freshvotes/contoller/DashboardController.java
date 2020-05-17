package com.freshvotes.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.ProductRepository;

@Controller
public class DashboardController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/")
	public String rootView() {
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, @AuthenticationPrincipal User user) {
		List<Product> products = productRepo.findByUser(user);
		model.addAttribute("products", products);
		return "dashboard";
	}
	
	@PostMapping("/dashboard")
	public String createProduct(@AuthenticationPrincipal User user) {
		Product product = new Product(); 
		product.setPublished(false);
		product.setUser(user);
		
		product = productRepo.save(product);
		
		return "redirect:/products/" + product.getId();
	}
}
