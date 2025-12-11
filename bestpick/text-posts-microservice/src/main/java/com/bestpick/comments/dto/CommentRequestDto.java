package com.bestpick.comments.dto;

public record CommentRequestDto(Long userId, String commentBody, String postId) {

}
