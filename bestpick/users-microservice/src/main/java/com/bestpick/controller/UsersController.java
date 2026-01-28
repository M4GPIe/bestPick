package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.dto.SpringErrorDto;
import com.bestpick.dto.UserRequestDto;
import com.bestpick.dto.UserResponseDto;
import com.bestpick.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;

    @Operation(summary = "Creates a user and returns its information", description = "Either internal user with username and password or external with username and sub verififcation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created succesfully", content = @Content(schema = @Schema(implementation = UserRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Missing required parameters", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "Given username is already in use", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "503", description = "Profile image store error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user) {

        UserResponseDto createdUser = userService.createUser(user);

        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Retrieve user list", description = "Retrieves all user matching the given params. No params returns all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched succesfully", content = @Content(schema = @Schema(implementation = UserRequestDto[].class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
    })
    @GetMapping
    public ResponseEntity<UserResponseDto[]> getUsers(@RequestParam(required = false) String username) {

        UserResponseDto[] users = userService.getAllUsers(username);

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by id", description = "Returns the information of a user given its unique internal id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid user id. User id must be valid number", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {

        UserResponseDto user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user information", description = "Updates the user and returns the new user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated correctly"),
            @ApiResponse(responseCode = "400", description = "User id must be valid number", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> putMethodName(@PathVariable String id, @RequestBody UserRequestDto user) {

        UserResponseDto updatedUser = userService.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user by id", description = "WARNING! Deletes all user data, posts and relations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted correctly"),
            @ApiResponse(responseCode = "400", description = "User id must be valid number", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {

        userService.deleteUserById(id);

        return ResponseEntity.ok("User deleted correctly");
    }

    @Operation(summary = "Get is username taken", description = "Returns true if the username is already being used by other user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Username checked correctly"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @GetMapping("/isUsernameTaken/{username}")
    public ResponseEntity<Boolean> getMethodName(@PathVariable String username) {
        boolean taken = userService.isUsernameTaken(username);

        return ResponseEntity.ok(taken);
    }

    @Operation(summary = "Check if internal login is valid", description = "Receives the login info from auth ms and returns true if is valid or false if the given credentials are incorrect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Body contains if login is correct or not")
    })
    @GetMapping("/checkIsValidLogin/internal")
    public ResponseEntity<Boolean> checkIsValidInternalLogin(@RequestParam String username,
            @RequestParam String password) {
        boolean isValid = userService.isValidInternalLogin(username, password);

        return ResponseEntity.ok(isValid);
    }

    @Operation(summary = "Check if external login is valid", description = "Receives the login info from auth ms and returns true if is valid or false if the given credentials are incorrect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Body contains if login is correct or not")
    })
    @GetMapping("/checkIsValidLogin/external")
    public ResponseEntity<Boolean> checkIsValidExternalLogin(@RequestParam String username,
            @RequestParam String sub) {
        boolean isValid = userService.isValidExternalLogin(username, sub);

        return ResponseEntity.ok(isValid);
    }
}
