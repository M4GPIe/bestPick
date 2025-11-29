package com.bestpick.comments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bestpick.testPosts.model.TextPost;

@Data
@Document(value = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    private String userId;

    @DBRef
    private TextPost textPost;

    private String commentbody;

}
