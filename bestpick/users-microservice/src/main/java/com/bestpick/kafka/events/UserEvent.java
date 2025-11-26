package com.bestpick.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private Long id;
    private String username;
    private String description;
    private String profileImagePath;
}