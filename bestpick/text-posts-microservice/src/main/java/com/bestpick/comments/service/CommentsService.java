package com.bestpick.comments.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.comments.dto.CommentDto;
import com.bestpick.comments.dto.CommentRequestDto;
import com.bestpick.comments.model.Comment;
import com.bestpick.comments.model.CommentMetadata;
import com.bestpick.comments.repository.CommentsRepository;

public class CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    public List<CommentDto> getComments(String userId, String postId) {

        if (userId != null && postId != null) {
            return commentsRepository.findByUserIdAndTextPost_Id(userId, postId)
                    .stream().map(Comment::toDto).toList();
        }

        if (userId != null) {
            return commentsRepository.findByUserId(userId)
                    .stream().map(Comment::toDto).toList();
        }

        if (postId != null) {
            return commentsRepository.findByTextPost_Id(postId)
                    .stream().map(Comment::toDto).toList();
        }

        return commentsRepository.findAll().stream().map(Comment::toDto).toList();
    }

    public CommentDto updateComment(String commentId, String commentBody) {

        if (commentId == null || commentId.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commentId should be a valid mongoDB id");
        }

        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "comment not found on DB"));

        comment.setCommentbody(commentBody);

        CommentMetadata metadata = comment.getCommentMetadata();

        metadata.setLastModified(Instant.now());

        comment.setCommentMetadata(metadata);

        return Comment.toDto(commentsRepository.save(comment));
    }

    public CommentDto postComment(CommentRequestDto newComment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

    public void deleteComment(String commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
    }

}
