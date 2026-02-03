package com.bestpick.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer vote; // [-3 , 3]

    private PostType postType;

    private VoteMetadata metadata;

    public static boolean isValid(Vote vote) {
        return vote.getPostId() != null &&
                vote.getPostType() != null &&
                vote.getUserId() != null &&
                vote.getVote() != null;
    }

}
