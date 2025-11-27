package com.bestpick.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    private Long userId;

    // the higher score, the better recommendation
    private double score;

}
