package com.bestpick.textPosts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.comments.repository.CommentsRepository;
import com.bestpick.textPosts.dto.TextPostDto;
import com.bestpick.textPosts.dto.TextPostRequestDto;
import com.bestpick.textPosts.model.PostMetadata;
import com.bestpick.textPosts.model.TextPost;
import com.bestpick.textPosts.repository.TextPostRepository;

import java.time.Instant;

@Service
public class TextPostService {

    @Autowired
    TextPostRepository textPostRepository;

    @Autowired
    CommentsRepository commentsRepository;

    public List<TextPostDto> getTextPosts(Long userId, String[] hashTags) {

        List<String> hashtagsList = new ArrayList<String>();

        if (hashTags != null) {
            hashtagsList = List.of(hashTags);
        }

        List<TextPost> textPosts = textPostRepository.findByUserIdAndHashtags(userId, hashtagsList);

        return textPosts.stream().map(TextPost::toDto).toList();
    }

    @Transactional
    public void deletePost(String id) {
        if (id == null || id.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id parameter must be valid mongoDB id");
        }
        textPostRepository.deleteById(id);
        commentsRepository.deleteByTextPostIdIn(List.of(new ObjectId(id)));
    }

    public TextPostDto createTextPost(TextPostRequestDto post) {

        Instant now = Instant.now();

        PostMetadata metadata = new PostMetadata(now, now);

        Map<String, Integer> hashtags = findHashtagsInText(post.postBody());

        TextPost newPost = new TextPost(null, post.userId(), post.postBody(), metadata, hashtags);

        TextPost savedPost = textPostRepository.save(newPost);

        return TextPost.toDto(savedPost);

    }

    public TextPostDto updateTextPost(String id, TextPostRequestDto post) {

        if (id == null || id.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Id parameter must be valid mongoDB id");
        }

        TextPost existingPost = textPostRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found on DB"));

        // update hashtags
        Map<String, Integer> newHashtags = findHashtagsInText(post.postBody());

        PostMetadata metadata = existingPost.getPostMetadata();

        metadata.setLastModified(Instant.now());

        existingPost.setPostMetadata(metadata);
        existingPost.setPostBody(post.postBody());
        existingPost.setHashtags(newHashtags);

        TextPost savedPost = textPostRepository.save(existingPost);

        return TextPost.toDto(savedPost);
    }

    private Map<String, Integer> findHashtagsInText(String text) {
        Map<String, Integer> hashtagCounter = new HashMap<>();

        Pattern HASHTAG_PATTERN = Pattern.compile("#(\\S+)");

        Matcher matcher = HASHTAG_PATTERN.matcher(text);

        while (matcher.find()) {
            String hashtag = matcher.group(1);

            hashtagCounter.merge(hashtag, 1, (old, curr) -> old + curr);
        }

        return hashtagCounter;
    }

}
