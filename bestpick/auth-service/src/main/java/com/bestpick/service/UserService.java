package com.bestpick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.dto.ExternalUserLoginDto;
import com.bestpick.dto.InternalUserLoginDto;
import com.bestpick.model.User;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class UserService {

    @Autowired
    JWTService jwtService;

    @Autowired
    WebClient.Builder webClientBuilder;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Mono<String> loginUser(InternalUserLoginDto userDto) {

        return webClientBuilder.build()
                .get()
                .uri("http://users-microservice/api/users",
                        uriBuilder -> uriBuilder.queryParam("username", userDto.username()).build())
                .retrieve()
                .bodyToMono(User[].class)
                .flatMap(userArray -> {
                    if (userArray == null || userArray.length == 0) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
                    }

                    User user = userArray[0];

                    // BCrypt es bloqueante, así que lo sacamos a boundedElastic
                    return Mono.fromCallable(() -> {
                        if (encoder.matches(userDto.password(), user.getPasswordHash())) {
                            return jwtService.generateToken(user);
                        } else {
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
                        }
                    }).subscribeOn(Schedulers.boundedElastic());
                });
    }

    public Mono<String> loginExternalUser(ExternalUserLoginDto userDto) {
        return webClientBuilder.build()
                .get()
                .uri("http://users-microservice/api/users",
                        uriBuilder -> uriBuilder.queryParam("username", userDto.username()).build())
                .retrieve()
                .bodyToMono(User[].class)
                .flatMap(userArray -> {
                    if (userArray == null || userArray.length == 0) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or sub");
                    }

                    User user = userArray[0];
                    if (user.getSub().equals(userDto.sub())) {
                        return Mono.just(jwtService.generateToken(user));
                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or sub");
                    }
                });
    }

}
