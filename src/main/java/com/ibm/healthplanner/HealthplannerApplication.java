package com.ibm.healthplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class HealthplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthplannerApplication.class, args);
	}

	@Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
