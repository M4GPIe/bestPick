package com.bestpick.testPosts.repository;

import java.util.List;

import com.bestpick.testPosts.model.TextPost;

public interface TextPostRepositoryCustom {

    List<TextPost> findByUserIdAndHashtags(String userId, List<String> hashtags);
}
