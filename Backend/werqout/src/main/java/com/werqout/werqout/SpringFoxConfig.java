package com.werqout.werqout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.swagger.models.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

/*
 * Swagger implementation
 * 
 * Find raw api values by going to http://coms-309-034.cs.iastate.edu:8080/v2/api-docs
 * Find swagger ui by going to http://coms-309-034.cs.iastate.edu:8080/swagger-ui/#/
 */

@Configuration
@EnableSwagger2WebMvc
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
}
