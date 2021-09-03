/*
 * 
 *  @author Evan Uhlmeyer
 *  ComS 309
 *  Spring Boot test two - Handling basic requests
 *  
 *  This contains a few simple tests with Spring Boot in order to build a base understanding of how the framework works.
 *  
 *  Code referenced and used as a guide can be found at:
 *  https://git.linux.iastate.edu/cs309/tutorials/tree/springboot_unit1_1_basicrequest
 *  https://spring.io/guides/gs/spring-boot/
 *  
 */


package com.exp2.application;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
	// Entry point for application
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// Function prints beans provided by Spring boot by default when application is initialized
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			
			System.out.println("Let's inspect the beans provided by spring boot:");
			
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for(String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}

}
