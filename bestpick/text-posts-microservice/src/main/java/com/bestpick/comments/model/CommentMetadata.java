package com.bestpick.comments.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentMetadata {
    private Instant creationDate;

    private Instant lastModified;
}
