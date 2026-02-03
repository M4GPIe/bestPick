package com.bestpick.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record UserRequestDto(
                @Schema(description = "User name", requiredMode = RequiredMode.REQUIRED)

                String username,

                @Schema(description = "Password for internal login. If user is external e.g. google login should be null", requiredMode = RequiredMode.NOT_REQUIRED)

                String password,

                @Schema(description = "User id on external login provider. If user logs with password should be null", requiredMode = RequiredMode.NOT_REQUIRED)

                String uid,

                @Schema(description = "External provider", requiredMode = RequiredMode.NOT_REQUIRED)

                String iss,

                @Schema(description = "User description", requiredMode = RequiredMode.NOT_REQUIRED)

                String description,

                @Schema(description = "User profile picture", requiredMode = RequiredMode.NOT_REQUIRED)

                MultipartFile profilePhoto) {

}
