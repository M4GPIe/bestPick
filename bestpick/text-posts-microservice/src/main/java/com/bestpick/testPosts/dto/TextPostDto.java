package com.bestpick.testPosts.dto;

import com.bestpick.testPosts.model.PostMetadata;

public record TextPostDto(
        String id,
        String userId,
        String postBody,
        String timestamp,
        PostMetadata postMetadata) {
}
