package com.bestpick.textPosts.dto;

import com.bestpick.textPosts.model.PostMetadata;

public record TextPostDto(
        String id,
        String userId,
        String postBody,
        PostMetadata postMetadata) {
}
