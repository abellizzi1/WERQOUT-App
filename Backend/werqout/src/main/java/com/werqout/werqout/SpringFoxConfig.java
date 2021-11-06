package com.werqout.werqout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

/*
 * Swagger implementation
 * 
 * Find raw api values by going to http://coms-309-034.cs.iastate.edu:8080/v2/api-docs
 * Find swagger ui by going to http://coms-309-034.cs.iastate.edu:8080/swagger-ui/#/
 */

@Configuration
@Import(SpringDataRestConfiguration.class)
public class SpringFoxConfig {
	
	public static final String ATHLETE_TAG = "Athlete controller";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
        //enabling swagger-ui part for visual documentation
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
