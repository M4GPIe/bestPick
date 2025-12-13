package com.bestpick.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

enum PostType {
    TEXT,
    MEDIA
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class VoteId implements Serializable {
    private String userId;
    private String postId;
}

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@IdClass(VoteId.class)
@Table(name = "votes")
public class Vote {

    @Id
    private Long userId;

    @Id
    private String postId;

    private int vote; // [-3 , 3]

    private PostType postType;

    private VoteMetadata metadata;

}
