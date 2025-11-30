package com.bestpick.comments.dto;

import com.bestpick.comments.model.CommentMetadata;

public record CommentDto(
        String id,
        String userId,
        String commentBody,
        String postId,
        CommentMetadata commentMetadata) {
}
