package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.dto.SpringErrorDto;
import com.bestpick.dto.ExternalUserLoginDto;
import com.bestpick.dto.SignUpDto;
import com.bestpick.service.JWTService;
import com.bestpick.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    // @Operation(summary = "Internal login", description = "Logs user with username
    // and password and returns jwt auth token")
    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "User logged successfully"),
    // @ApiResponse(responseCode = "401", description = "Wrong username or
    // password", content = @Content(schema = @Schema(implementation =
    // SpringErrorDto.class))),
    // @ApiResponse(responseCode = "500", description = "Internal server error",
    // content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    // })
    // @PostMapping("/login/userPassword")
    // public Mono<String> login(@RequestBody InternalUserLoginDto user) {
    // return userService.loginUser(user);
    // }

    @Operation(summary = "External login e.g. Google auth", description = "Logs user external user with username and external provider user id (sub)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged successfully"),
            @ApiResponse(responseCode = "401", description = "Wrong username or sub", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/login")
    public Mono<String> loginExternal(@RequestBody ExternalUserLoginDto user) {
        return userService.loginExternalUser(user);
    }

    @Operation(summary = "Signup user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/signup")
    public Mono<String> signUp(@RequestBody SignUpDto user) {
        return userService.signUpUser(user);
    }

    @Operation(summary = "Check if token is valid", description = "Returns true if jwt token is valid and not expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Token must not be null", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/verifyToken")
    public Boolean postMethodName(@RequestBody Map<String, String> body) {

        String token = body.get("token");

        return jwtService.isValid(token);
    }

}
