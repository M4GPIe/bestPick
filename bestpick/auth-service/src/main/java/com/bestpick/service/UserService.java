package com.bestpick.service;

import java.sql.Array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bestpick.dto.UserLoginDto;
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

    public Mono<String> loginUser(UserLoginDto userDto) {

        return webClientBuilder.build()
                .get()
                .uri("http://users-microservice/api/users",
                        uriBuilder -> uriBuilder.queryParam("username", userDto.username()).build())
                .retrieve()
                .bodyToMono(User[].class)
                .flatMap(userArray -> {
                    if (userArray == null) {
                        return Mono.just("User not found");
                    }

                    User user = userArray[0];

                    // BCrypt es bloqueante, así que lo sacamos a boundedElastic
                    return Mono.fromCallable(() -> {
                        if (encoder.matches(userDto.password(), user.getPasswordHash())) {
                            return jwtService.generateToken(user);
                        } else {
                            return "Wrong password";
                        }
                    })
                            .subscribeOn(Schedulers.boundedElastic());
                });
    }

}
