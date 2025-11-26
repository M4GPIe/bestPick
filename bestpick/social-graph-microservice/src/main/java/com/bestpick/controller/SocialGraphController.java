package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.dto.RelationshipDto;
import com.bestpick.model.Recommendation;
import com.bestpick.service.SocialGraphService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/recommendations/{id}")
    public ResponseEntity<Recommendation[]> getUserRecommendations(@PathVariable String userId) {

        List<Recommendation> recommendations = socialGraphService.getUserRecommendations(userId);

        return ResponseEntity.ok(recommendations.toArray(Recommendation[]::new));

    }

}
