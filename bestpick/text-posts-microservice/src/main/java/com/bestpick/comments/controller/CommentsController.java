package com.bestpick.comments.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.comments.dto.CommentDto;
import com.bestpick.comments.dto.CommentRequestDto;
import com.bestpick.comments.service.CommentsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/text-posts/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(@RequestParam(required = false) Long userId,
            @RequestParam(required = false) String postId) {
        List<CommentDto> comments = commentsService.getComments(userId, postId);

        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> putMethodName(@PathVariable String commentId,
            @RequestBody CommentRequestDto newComment) {
        CommentDto updatedComment = commentsService.updateComment(commentId, newComment);

        return ResponseEntity.ok(updatedComment);
    }

    @PostMapping
    public ResponseEntity<CommentDto> postComment(@RequestBody CommentRequestDto newComment) {
        CommentDto postedComment = commentsService.postComment(newComment);

        return ResponseEntity.ok(postedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId) {
        commentsService.deleteComment(commentId);

        return ResponseEntity.ok("Comment deleted correctly");
    }

}
