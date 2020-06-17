package com.freshvotes.contoller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.ProductRepository;

@Controller
public class DashboardController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/")
	public String rootView() {
		return "redirect:/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(@RequestParam(value="search", required=false) String search,
							Model model) {
		List<Product> products;
		
		if(search != null) {
			products = productRepo.findByPublishedAndNameContaining(true, search);
		}
		else {			
			products = productRepo.findByPublished(true);
		}
		model.addAttribute("products", products);
		return "dashboard";
	}
	
	@GetMapping("/dashboard/createProduct")
	public String showCreateProduct(@AuthenticationPrincipal User user,
									Model model) {
		Product product = new Product(); 
		product.setPublished(false);
		product.setUser(user);
	
		model.addAttribute("product", product);
		model.addAttribute("productExist", false);
		
		return "product";
	}
	
	@PostMapping("/dashboard/createProduct")
	public String createProduct(Product product) throws UnsupportedEncodingException {
		product = productRepo.save(product);
		return "redirect:/products/" + URLEncoder.encode(product.getName(), StandardCharsets.UTF_8.name());
	}
	
	@GetMapping("/dashboard/private")
	public String getMyProducts(@RequestParam(value="search", required=false) String search,
								Model model,
								@AuthenticationPrincipal User user) {
		List<Product> products;
		
		if(search != null) {
			products = productRepo.findByUserAndNameContaining(user, search);
		}
		else {			
			products = productRepo.findByUser(user);
		}
		
		model.addAttribute("products", products);
		return "dashboard-private";
	}

}








