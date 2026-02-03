package com.bestpick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.dto.ExternalUserLoginDto;
import com.bestpick.dto.InternalUserLoginDto;
import com.bestpick.dto.SignUpDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {

    @Autowired
    JWTService jwtService;

    @Autowired
    WebClient.Builder webClientBuilder;

    public Mono<String> loginUser(InternalUserLoginDto userDto) {

        return webClientBuilder.build()
                .get()
                .uri("http://users-microservice/api/users/checkIsValidLogin/internal",
                        uriBuilder -> uriBuilder
                                .queryParam("username", userDto.username())
                                .queryParam("password", userDto.password())
                                .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .map(isValid -> {
                    if (isValid) {
                        return jwtService.generateToken();
                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
                    }
                });
    }

    public Mono<String> loginExternalUser(ExternalUserLoginDto userDto) {
        return webClientBuilder.build()
                .get()
                .uri("http://users-microservice/api/users/checkIsValidLogin/external",
                        uriBuilder -> uriBuilder
                                .queryParam("uid", userDto.uid())
                                .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .map(isValid -> {
                    if (isValid) {
                        return jwtService.generateToken();
                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
                    }
                });
    }

    public Mono<String> signUpUser(SignUpDto user) {
        return webClientBuilder.build()
                .post()
                .uri("http://users-microservice/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(SignUpDto.class)
                .map(createdUser -> {
                    if (createdUser != null) {
                        return jwtService.generateToken();
                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
                    }
                });
    }

}
