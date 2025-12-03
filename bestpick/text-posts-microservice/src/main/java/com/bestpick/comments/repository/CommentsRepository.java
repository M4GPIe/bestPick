package com.bestpick.comments.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestpick.comments.model.Comment;

public interface CommentsRepository extends MongoRepository<Comment, String> {

    List<Comment> findByUserIdAndTextPost_Id(String userId, String postId);

    List<Comment> findByUserId(String userId);

    List<Comment> findByTextPost_Id(String postId);

}
