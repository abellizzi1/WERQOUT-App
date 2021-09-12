package com.javatpoint.springboothelloworldexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.javatpoint.controller.HelloWorldController;

@SpringBootApplication
@ComponentScan(basePackageClasses = HelloWorldController.class)
public class SpringBootHelloWorldExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldExampleApplication.class, args);
	}

}
