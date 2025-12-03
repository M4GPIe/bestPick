package com.bestpick.testPosts.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.testPosts.dto.TextPostDto;
import com.bestpick.testPosts.dto.TextPostRequestDto;
import com.bestpick.testPosts.model.PostMetadata;
import com.bestpick.testPosts.model.TextPost;
import com.bestpick.testPosts.repository.TextPostRepository;

import java.time.Instant;

public class TextPostService {

    @Autowired
    TextPostRepository textPostRepository;

    public List<TextPostDto> getTextPosts(String userId, String[] hashTags) {

        List<TextPost> textPosts = textPostRepository.findByUserIdAndHashtags(userId, List.of(hashTags));

        return textPosts.stream().map(TextPost::toDto).toList();
    }

    public void deletePost(String id) {
        if (id == null || id.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id parameter must be valid mongoDB id");
        }
        textPostRepository.deleteById(id);
    }

    public TextPostDto createTextPost(TextPostRequestDto post) {

        Instant now = Instant.now();

        PostMetadata metadata = new PostMetadata(now, now);

        Map<String, Integer> hashtags = findHashtagsInText(post.postBody());

        TextPost newPost = new TextPost(null, post.userId(), post.postBody(), metadata, hashtags);

        TextPost savedPost = textPostRepository.save(newPost);

        return TextPost.toDto(savedPost);

    }

    private Map<String, Integer> findHashtagsInText(String text) {
        Map<String, Integer> hashtagCounter = new HashMap<>();

        Pattern HASHTAG_PATTERN = Pattern.compile("#(\\S+)");

        Matcher matcher = HASHTAG_PATTERN.matcher(text);

        while (matcher.find()) {
            String hashtag = matcher.group(1);

            hashtagCounter.merge(hashtag, 1, Integer::sum);
        }

        return hashtagCounter;
    }

    public TextPostDto updateTextPost(String id, TextPostRequestDto post) {

        if (id == null || id.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Id parameter must be valid mongoDB id");
        }

        TextPost existingPost = textPostRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found on DB"));

        BeanUtils.copyProperties(post, existingPost, "id");

        PostMetadata metadata = existingPost.getPostMetadata();

        metadata.setLastModified(Instant.now());

        existingPost.setPostMetadata(metadata);

        TextPost savedPost = textPostRepository.save(existingPost);

        return TextPost.toDto(savedPost);
    }

}
