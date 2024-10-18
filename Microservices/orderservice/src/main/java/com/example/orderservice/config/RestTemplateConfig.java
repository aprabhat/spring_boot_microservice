package com.example.orderservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import brave.Tracer;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder, Tracer tracer) {
		return builder.build();
	}
}
