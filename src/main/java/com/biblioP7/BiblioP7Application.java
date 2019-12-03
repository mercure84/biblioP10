package com.biblioP7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;



@SpringBootApplication
@EnableFeignClients
public class BiblioP7Application {


	private static final Logger logger = LoggerFactory.getLogger((BiblioP7Application.class));

	public static void main(String[] args) {
		SpringApplication.run(BiblioP7Application.class, args);

		logger.warn(" L'application BiblioP7 est lancée !");
	}

}
