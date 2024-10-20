package com.example.gateway.filter;

import java.time.LocalDateTime;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ResponseHeaderFilter extends AbstractGatewayFilterFactory<ResponseHeaderFilter.Config> {

	public ResponseHeaderFilter() {
		super(Config.class);
	}

	public static class Config {
		// Configuration properties for the filter (if any)
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			// Add the X-Response-Time header with the current time
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				exchange.getResponse().getHeaders().add("X-Response-Time", LocalDateTime.now().toString());
			}));
		};
	}
}
