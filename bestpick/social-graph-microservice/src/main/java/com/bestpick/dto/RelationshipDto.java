package com.bestpick.dto;

// Dto that simplifies every user relationship: fromUser ---> toUser
// Could be used either to following, blocking or any other relation implemented
public record RelationshipDto(Long fromUserId, Long toUserId) {

}
