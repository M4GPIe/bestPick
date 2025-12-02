package com.bestpick.testPosts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        if (id == null || id.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id parameter must be valid mongoDB id");
        }
        textPostRepository.deleteById(id);
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
