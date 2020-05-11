package com.freshvotes.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping("/")
	public String rootView() {
		return "index";
	}
}
