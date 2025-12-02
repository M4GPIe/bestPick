package com.bestpick.testPosts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.testPosts.dto.TextPostDto;
import com.bestpick.testPosts.dto.TextPostRequestDto;
import com.bestpick.testPosts.service.TextPostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/text-posts/posts")
public class TextPostsController {

    @Autowired
    TextPostService textPostService;

    @GetMapping()
    public ResponseEntity<TextPostDto[]> getTextPosts(@RequestParam String userId,
            @RequestParam String[] hashTags) {
        List<TextPostDto> textPosts = textPostService.getTextPosts(userId, hashTags);

        return ResponseEntity.ok(textPosts.toArray(TextPostDto[]::new));
    }

    @PostMapping()
    public ResponseEntity<TextPostDto> postMethodName(@RequestBody TextPostRequestDto post) {
        TextPostDto createdPost = textPostService.createTextPost(post);

        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextPostDto> putMethodName(@PathVariable String id, @RequestBody TextPostRequestDto post) {
        TextPostDto updatedPost = textPostService.updateTextPost(id, post);

        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {

        textPostService.deletePost(id);

        return ResponseEntity.ok("Post deleted correctly");
    }

}
