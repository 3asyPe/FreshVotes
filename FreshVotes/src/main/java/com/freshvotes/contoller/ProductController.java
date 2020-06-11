package com.freshvotes.contoller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.ProductRepository;
import com.freshvotes.service.FeatureService;

@Controller
public class ProductController {
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private FeatureService featureService;
	
	@GetMapping("/products/{productId}/edit")
	public String editProduct(@PathVariable int productId,
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
	
	@GetMapping("/products/{productName}")
	public String productUserView(@PathVariable String productName, Model model,
								  @RequestParam(required = false) String[] filters,
								  HttpServletResponse response,
								  @AuthenticationPrincipal User user) throws IOException {
		if(productName != null) {
			try {
				String decodedProductName = URLDecoder.decode(productName, StandardCharsets.UTF_8.name());
			
				Optional<Product> productOpt = productRepo.findByName(decodedProductName);
				
				if(productOpt.isPresent()) {
					Product product = productOpt.get();
					List<Feature> features;
					
					if(filters == null || filters.length == 0) {
						filters = new String[]{"Review", "Pending review"};
						features = featureService.findByProductAndStatusIn(product, filters);
					}
					else if(filters[0].equals("all")) {
						features = featureService.findByProduct(product);
					}
					else {
						features = featureService.findByProductAndStatusIn(product, filters);	
					}
					
					for(String filter : filters) {
						model.addAttribute(filter, true);
					}
					
					features.sort((Feature f1, Feature f2) -> f2.countVotes() - f1.countVotes());
					
					model.addAttribute("product", productOpt.get());
					model.addAttribute("user", user);
					model.addAttribute("features", features);
				}
				else {
					response.sendError(HttpStatus.NOT_FOUND.value(), "There is no product with name " + productName);
				}
			} catch (UnsupportedEncodingException exc) {
				log.error("There was an error decoding a product URL", exc);
			}
			productRepo.findByName(productName);
		}
		
		return "productUserView";
	}
	
	@PostMapping("/products/{productId}/edit")
	public String saveProduct(Product product, @PathVariable int productId) throws UnsupportedEncodingException {
		productRepo.save(product);
		return "redirect:/products/" + URLEncoder.encode(product.getName(), StandardCharsets.UTF_8.name());
	}
	
	@PostMapping("/products/{productId}/delete")
	public String deleteProduct(@PathVariable int productId) {
		productRepo.deleteById(productId);
		return "redirect:/dashboard";
	}
}













