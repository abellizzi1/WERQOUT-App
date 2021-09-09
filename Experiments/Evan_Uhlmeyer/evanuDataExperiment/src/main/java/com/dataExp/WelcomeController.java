package com.dataExp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Definition of what the splash page at / will desplay

@RestController
public class WelcomeController {
	
	@GetMapping("/")
	public String welcom() {
		return "Welcome to Evan Uhlmeyer's COMS309 Code!";
	}

}
