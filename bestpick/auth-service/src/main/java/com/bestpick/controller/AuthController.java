package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.dto.UserLoginDto;
import com.bestpick.service.JWTService;
import com.bestpick.service.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping("/login/userPassword")
    public Mono<String> login(@RequestBody UserLoginDto user) {
        return userService.loginUser(user);
    }

    @PostMapping("/login/external")
    public Mono<String> loginExternal(@RequestBody UserLoginDto user) {
        return userService.loginExternalUser(user);
    }

    @PostMapping("/verifyToken")
    public Boolean postMethodName(@RequestBody Map<String, String> body) {

        String token = body.get("token");

        return jwtService.isValid(token);
    }

}
