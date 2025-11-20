package com.bestpick.apigateway.config.routes;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ApiKeyFilter implements GatewayFilter {

    // TODO: save frontend api key on docker env

    String frontendApiKey = "asdadni34e732yr4e23ndwd8923hednd2893eh293dnwq";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null) {

            String token = authHeader.replace("Bearer ", "");

            log.info("User token:: {}", token);

            if (token.equals(frontendApiKey)) {
                return chain.filter(exchange);
            }

        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }

}
