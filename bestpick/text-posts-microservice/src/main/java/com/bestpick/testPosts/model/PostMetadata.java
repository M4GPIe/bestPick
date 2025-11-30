package com.bestpick.testPosts.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMetadata {
    private Instant creationDate;

    private Instant lastModified;
}
