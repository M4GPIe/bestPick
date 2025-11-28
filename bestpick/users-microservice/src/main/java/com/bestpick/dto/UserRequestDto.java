package com.bestpick.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserRequestDto(String username, String password, String description, MultipartFile profilePhoto,
        String iss, String sub) {

}
