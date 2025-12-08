package com.bestpick.textPosts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestpick.textPosts.model.TextPost;

public interface TextPostRepository extends MongoRepository<TextPost, String>, TextPostRepositoryCustom {

}
