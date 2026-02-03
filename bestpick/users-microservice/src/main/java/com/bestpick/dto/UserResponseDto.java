package com.bestpick.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record UserResponseDto(
        @Schema(description = "User name", requiredMode = RequiredMode.REQUIRED)

        String username,

        @Schema(description = "Password for internal login. If user is external e.g. google login should be null", requiredMode = RequiredMode.NOT_REQUIRED)

        String passwordHash,

        @Schema(description = "User description", requiredMode = RequiredMode.NOT_REQUIRED)

        String description,

        @Schema(description = "Server storage path for profile image", requiredMode = RequiredMode.NOT_REQUIRED)

        String profileImagePath,

        @Schema(description = "External provider", requiredMode = RequiredMode.NOT_REQUIRED)

        String iss,

        @Schema(description = "User email", requiredMode = RequiredMode.NOT_REQUIRED)

        String email,

        @Schema(description = "User id on external login provider. If user logs with password should be null", requiredMode = RequiredMode.NOT_REQUIRED)

        String uid

) {

}