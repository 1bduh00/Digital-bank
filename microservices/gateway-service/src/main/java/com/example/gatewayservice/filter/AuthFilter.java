package com.example.gatewayservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Implement your filter logic here
            // For example, you can modify request or response headers, log requests, etc.
            System.out.println("Executing CustomGlobalFilter");

            // Continue processing the request by calling the next filter in the chain
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Add configuration properties here if needed
    }
}
