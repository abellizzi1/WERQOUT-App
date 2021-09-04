package com.exp2.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {

	// Function handles get requests for the root directory. Prints a simple message when a get request is received
	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	// Taking a parameter with a get request. For example the request <localhost:8080/getTest?username=uhlmeyer> returns "Hi uhlmeyer, you sent a get request"
	@GetMapping("/getTest")
	public String getTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		return String.format("Hi %s, you sent a get request", message);
	}
	
	@PostMapping("/postTest")
	public String postTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		return String.format("Hi %s, post request", message);
	}
	
	// Handling of post requests at root directory. Prints a simple message when a get request is received
	@PostMapping("/")
	public String postTest() {
		return "Post Request";
	}
	
}
