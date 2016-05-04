package it.smartcommunitylab.tataapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
	// extends SpringBootServletInitializer

	// @Override
	// protected SpringApplicationBuilder configure(SpringApplicationBuilder
	// application) {
	// return application.sources(WebApplication.class);
	// }

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
