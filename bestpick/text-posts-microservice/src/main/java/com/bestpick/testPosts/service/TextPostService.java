package com.bestpick.testPosts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestpick.testPosts.dto.TextPostDto;
import com.bestpick.testPosts.dto.TextPostRequestDto;
import com.bestpick.testPosts.repository.TextPostRepository;

public class TextPostService {

    @Autowired
    TextPostRepository textPostRepository;

    public List<TextPostDto> getTextPosts(String userId, String[] hashTags) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTextPosts'");
    }

    public void deletePost(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
    }

    public TextPostDto createTextPost(TextPostRequestDto post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTextPost'");
    }

    public TextPostDto updateTextPost(String id, TextPostRequestDto post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTextPost'");
    }

}
