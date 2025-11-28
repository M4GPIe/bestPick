package com.bestpick.apigateway.config.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ApiKeyFilter apiKeyFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/users")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.filter(apiKeyFilter))
                        .uri("lb://users-microservice"))

                .route(r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://users-microservice"))

                .route(r -> r.path("/api/social-graph/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://social-graph-microservice"))

                .route(r -> r.path("/api/text-posts/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://text-posts-microservice"))

                .route(r -> r.path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))

                .route(r -> r.path("/eureka/**")
                        .uri("http://localhost:8761"))

                // TODO: add frontend api key filter
                .route(r -> r.path("/auth/**")
                        .uri("lb://auth-microservice"))

                .build();
    }
}
