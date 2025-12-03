package com.bestpick.testPosts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bestpick.testPosts.model.TextPost;

public interface TextPostRepository extends MongoRepository<TextPost, String>, TextPostRepositoryCustom {

}
