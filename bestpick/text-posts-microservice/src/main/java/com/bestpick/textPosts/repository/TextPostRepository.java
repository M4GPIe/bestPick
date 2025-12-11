package com.bestpick.textPosts.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestpick.textPosts.model.TextPost;

public interface TextPostRepository extends MongoRepository<TextPost, String>, TextPostRepositoryCustom {
    Long deleteByUserId(Long userId);

    List<TextPost> findByUserId(Long userId);
}
