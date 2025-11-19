package com.bestpick.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserRequestDto(String username, String passwordHash, String description, MultipartFile profilePhoto) {

}
