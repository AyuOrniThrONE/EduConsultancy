package com.educon.api_gateway.config;

import com.educon.api_gateway.service.JwtService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class GatewayConfig {

    private final JwtService jwtService;

    public GatewayConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private JwtAuthorizationFilter getJwtAuthorizationFilter(List<String> roles) {
        return new JwtAuthorizationFilter(jwtService, roles);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // GUEST ROUTES FOR AUTH ROUTES OF USER-SERVICE
                .route("auth_route", r -> r.path("/auth/**")
                        .filters(f ->
                                f.rewritePath("/auth(?<segment>/?.*)", "/user-service/auth${segment}"))
                        .uri(USER_SERVICE_LB))

                // USER ROUTES FOR PROFILE ROUTE FUNCTION OF USER-SERVICE
                .route("user_profile", r -> r.path("/profile")
                        .filters(f -> f
                                .rewritePath("/profile(?<segment>/?.*)", "/user-service/profile${segment}")
                                .filter(getJwtAuthorizationFilter(ALL_ROLES)))
                        .uri(USER_SERVICE_LB))

                // GUEST ROUTES FOR THE BLOG SERVICE
                .route("blog_service_blogs", r -> r.path("/blogs/**")
                        .filters(f -> f.rewritePath("/blogs(?<segment>/?.*)", "/blog-service/blogs${segment}"))
                        .uri(BLOG_SERVICE_LB))
                .route("blog_service_tags", r -> r.path("/tags/**")
                        .filters(f -> f.rewritePath("/tags(?<segment>/?.*)", "/blog-service/tags${segment}"))
                        .uri(BLOG_SERVICE_LB))

                // GUEST ROUTES FOR THE PRODUCT SERVICE
                .route("product_service_courses", r -> r.path("/courses/**")
                        .filters(f -> f.rewritePath("/courses(?<segment>/?.*)", "/product-service/courses${segment}"))
                        .uri(PRODUCT_SERVICE_LB))
                .route("product_service_categories", r -> r.path("/categories/**")
                        .filters(f -> f.rewritePath("/categories(?<segment>/?.*)", "/product-service/categories${segment}"))
                        .uri(PRODUCT_SERVICE_LB))

                // USER ROUTES FOR THE PAYMENTS SERVICE
                .route("payment_service_tags", r -> r.path("/payments/**")
                        .filters(f -> f.rewritePath("/payments(?<segment>/?.*)", "/payment-service/payments${segment}")
                                .filter(getJwtAuthorizationFilter(ALL_ROLES)))
                        .uri(PAYMENT_SERVICE_LB))

                // ADMIN ROUTES FOR THE BLOG SERVICE
                .route("blog_service_admin_blogs", r -> r.path("/admin/blogs/**")
                        .filters(f ->
                                f.rewritePath("/admin/blogs(?<segment>/?.*)", "/blog-service/admin/blogs${segment}")
                                        .filter(getJwtAuthorizationFilter(ADMIN_ONLY)))
                        .uri(BLOG_SERVICE_LB))
                .route("blog_service_admin_tags", r -> r.path("/admin/tags/**")
                        .filters(f ->
                                f.rewritePath("/admin/tags(?<segment>/?.*)", "/blog-service/admin/tags${segment}")
                                        .filter(getJwtAuthorizationFilter(ADMIN_ONLY)))
                        .uri(BLOG_SERVICE_LB))

                // ADMIN ROUTES FOR THE PRODUCT SERVICE
                .route("product_service_admin_courses", r -> r.path("/admin/courses/**")
                        .filters(f ->
                                f.rewritePath("/admin/courses(?<segment>/?.*)", "/product-service/admin/courses${segment}")
                                        .filter(getJwtAuthorizationFilter(ADMIN_ONLY)))
                        .uri(PRODUCT_SERVICE_LB))
                .route("product_service_admin_categories", r -> r.path("/admin/categories/**")
                        .filters(f ->
                                f.rewritePath("/admin/categories(?<segment>/?.*)", "/product-service/admin/categories${segment}")
                                        .filter(getJwtAuthorizationFilter(ADMIN_ONLY)))
                        .uri(PRODUCT_SERVICE_LB))

                .build();
    }


    private static final List<String> ADMIN_ONLY = List.of("ADMIN");
    private static final List<String> STUDENT_ONLY = List.of("STUDENT");
    private static final List<String> PARENT_ONLY = List.of("PARENT");
    private static final List<String> ALL_ROLES = List.of("STUDENT", "PARENT", "ADMIN");

    private static final String USER_SERVICE_LB = "lb://USER-SERVICE";
    private static final String BLOG_SERVICE_LB = "lb://BLOG-SERVICE";
    private static final String PRODUCT_SERVICE_LB = "lb://PRODUCT-SERVICE";
    private static final String PAYMENT_SERVICE_LB = "lb://PAYMENT-SERVICE";
}

