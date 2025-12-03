package com.bestpick.testPosts.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostMetadata {
    private Instant creationDate;

    private Instant lastModified;
}
