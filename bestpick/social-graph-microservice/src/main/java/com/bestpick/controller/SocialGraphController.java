package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.dto.RelationshipDto;
import com.bestpick.model.Recommendation;
import com.bestpick.model.UserRelations;
import com.bestpick.service.SocialGraphService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/social-graph")
public class SocialGraphController {

    @Autowired
    private SocialGraphService socialGraphService;

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.follow(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User followed correctly");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.unfollow(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User unfollowed correctly");
    }

    @PostMapping("/isFollowing")
    public ResponseEntity<Boolean> isFollowing(@RequestBody RelationshipDto relationshipDto) {
        boolean isFollowing = socialGraphService.isFollowing(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok(isFollowing);
    }

    @PostMapping("/block")
    public ResponseEntity<String> block(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.block(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User blocked correctly");
    }

    @PostMapping("/unblock")
    public ResponseEntity<String> unblock(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.unblock(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User unblocked correctly");
    }

    @PostMapping("/isBlocking")
    public ResponseEntity<Boolean> isBlocking(@RequestBody RelationshipDto relationshipDto) {
        boolean isBlocking = socialGraphService.isBlocking(relationshipDto.fromUserId(), relationshipDto.toUserId());
        return ResponseEntity.ok(isBlocking);
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<Recommendation[]> getUserRecommendations(@PathVariable String userId) {

        Long parsedId;
        try {
            parsedId = Long.valueOf(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Recommendation> recommendations = socialGraphService.getUserRecommendations(parsedId);

        return ResponseEntity.ok(recommendations.toArray(Recommendation[]::new));

    }

    @GetMapping("/relations/{userId}")
    public ResponseEntity<UserRelations> getUserRelations(@PathVariable String userId) {

        UserRelations userRelations = socialGraphService.getUserRelations(userId);

        return ResponseEntity.ok(userRelations);
    }

}
