package com.bestpick.dto;

public record UserResponseDto(String username, String passwordHash, String description, String profileImagePath,
        String iss, String sub) {

}