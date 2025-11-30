package com.bestpick.testPosts.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(value = "text-posts")
@AllArgsConstructor
@NoArgsConstructor
public class TextPost {

    @Id
    private String id;

    private String userId;

    private String postBody;

    private PostMetadata postMetadata;

    private Map<String, Integer> hashtags;

}
