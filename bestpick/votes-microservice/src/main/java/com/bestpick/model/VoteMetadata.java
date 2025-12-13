package com.bestpick.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteMetadata {
    private Instant creationDate;
    private Instant lastModified;
}