package com.bestpick.model;

import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRelations {

    @Id
    private Long id;

    private Set<Long> followingIds;
    private Set<Long> followerIds;

    private Set<Long> blockingIds;
    private Set<Long> blockedByIds;

}
