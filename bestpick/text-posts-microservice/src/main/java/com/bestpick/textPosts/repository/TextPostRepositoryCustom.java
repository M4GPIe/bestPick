package com.bestpick.textPosts.repository;

import java.util.List;

import com.bestpick.textPosts.model.TextPost;

public interface TextPostRepositoryCustom {

    List<TextPost> findByUserIdAndHashtags(String userId, List<String> hashtags);
}
