package com.bestpick.comments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestpick.comments.dto.CommentDto;
import com.bestpick.comments.dto.CommentRequestDto;
import com.bestpick.comments.model.Comment;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
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
