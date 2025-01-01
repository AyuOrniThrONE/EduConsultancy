package com.educon.api_gateway.config;

import com.educon.api_gateway.exception.JwtExceptions;
import com.educon.api_gateway.service.JwtService;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthorizationFilter implements GatewayFilter {

    private final JwtService jwtService;
    private final List<String> requiredRoles;

    public JwtAuthorizationFilter(JwtService jwtService, List<String> requiredRoles) {
        this.jwtService = jwtService;
        this.requiredRoles = requiredRoles;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtExceptions("Unauthorized: Token is missing or invalid");
        }

        String token = authHeader.substring(7);
        if (jwtService.isTokenExpired(token)) {
            throw new JwtExceptions("JWT token is expired");
        }

        List<String> roles;
        String userId ;
        try {
            roles = jwtService.extractRoles(token);
            userId = jwtService.extractUserId(token);
        } catch (RuntimeException e) {
            throw new JwtExceptions("JWT token is expired");
        }

        if (roles == null || !hasRequiredRole(roles, requiredRoles)) {
            throw new JwtExceptions("Forbidden: You do not have the required permission");
        }
        exchange.getRequest().mutate()
                .header("X-User-Id",userId)
                .header("X-User-Roles", String.join(",", roles))
                .build();
        return chain.filter(exchange);
    }

    private boolean hasRequiredRole(List<String> userRoles, List<String> requiredRoles) {
        return userRoles.stream().anyMatch(requiredRoles::contains);
    }

    private Mono<Void> authFailureResponse(ServerWebExchange exchange, String message, Integer statusCode) {
        exchange.getResponse().setStatusCode(HttpStatus.valueOf(statusCode));
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(message.getBytes())));
    }
}
