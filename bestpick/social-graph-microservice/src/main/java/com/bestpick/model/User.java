package com.bestpick.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("User")

public class User {
    @Id
    private Long userId;

    private String username;

    // ids of users
    @Relationship(type = "FOLLOWWS", direction = Relationship.Direction.OUTGOING)
    private Set<User> following = new HashSet<>();

    // ids of users
    @Relationship(type = "FOLLOWED", direction = Relationship.Direction.OUTGOING)
    private Set<User> followedStrings = new HashSet<>();

}
