package com.bestpick.comments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bestpick.comments.dto.CommentDto;
import com.bestpick.textPosts.model.TextPost;

@Data
@Document(value = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    private Long userId;

    @DBRef
    private TextPost textPost;

    private String commentbody;

    private CommentMetadata commentMetadata;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getUserId(),
                comment.getCommentbody(),
                comment.getTextPost().getId(),
                comment.getCommentMetadata());
    }

}
