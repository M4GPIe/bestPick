package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.model.Vote;
import com.bestpick.model.VoteType;
import com.bestpick.service.VotesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/votes")
public class VotesController {

    @Autowired
    VotesService votesService;

    @PostMapping()
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        Vote createdVote = votesService.createVote(vote);

        return ResponseEntity.ok(createdVote);
    }

    @GetMapping()
    public ResponseEntity<List<Vote>> getVotes(@RequestParam(required = false) Long userId,
            @RequestParam(required = false) String postId,
            @RequestParam(required = false) VoteType type) {

        List<Vote> votes = votesService.getVotes(userId, postId, type);

        return ResponseEntity.ok(votes);
    }

    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long userId,
            @PathVariable String postId,
            @RequestBody Vote entity) {

        Vote updatedVote = votesService.updateVote(userId, postId, entity);

        return ResponseEntity.ok(updatedVote);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<String> deleteVote(@PathVariable Long userId,
            @PathVariable String postId) {

        votesService.deleteVote(userId, postId);

        return ResponseEntity.ok("Vote deleted correctly");
    }
}
