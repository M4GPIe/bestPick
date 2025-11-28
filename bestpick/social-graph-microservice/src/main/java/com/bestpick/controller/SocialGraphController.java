package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestpick.dto.RelationshipDto;
import com.bestpick.dto.SpringErrorDto;
import com.bestpick.model.Recommendation;
import com.bestpick.model.UserRelations;
import com.bestpick.service.SocialGraphService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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

    @Tag(name = "Follow controller", description = "All endpoints related with follow / unfollow user relations")
    @Operation(description = "Creates a follow relation fromUserId -FOLLOWS-> toUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed correctly"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.follow(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User followed correctly");
    }

    @Tag(name = "Follow controller")
    @Operation(description = "Deletes the relation fromUserId -FOLLOWS-> toUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unfollowed correctly"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.unfollow(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User unfollowed correctly");
    }

    @Tag(name = "Follow controller")
    @Operation(description = "Returns true if the relation fromUserId -FOLLOWS-> toUserId exists on DN. Else returns false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DB queried successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/isFollowing")
    public ResponseEntity<Boolean> isFollowing(@RequestBody RelationshipDto relationshipDto) {
        boolean isFollowing = socialGraphService.isFollowing(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok(isFollowing);
    }

    @Tag(name = "Block controller", description = "All endpoints related with block / unblock user relations")
    @Operation(description = "Creates the relation fromUserId -BLOCKS-> toUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User blocked correctly"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/block")
    public ResponseEntity<String> block(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.block(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User blocked correctly");
    }

    @Tag(name = "Block controller")
    @Operation(description = "Deletes the relation fromUserId -BLOCKS-> toUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unblocked correctly"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/unblock")
    public ResponseEntity<String> unblock(@RequestBody RelationshipDto relationshipDto) {
        socialGraphService.unblock(relationshipDto.fromUserId(), relationshipDto.toUserId());

        return ResponseEntity.ok("User unblocked correctly");
    }

    @Tag(name = "Block controller")
    @Operation(description = "Checks if the relation fromUserId -BLOCKS-> toUserId exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DB queried correctly"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @PostMapping("/isBlocking")
    public ResponseEntity<Boolean> isBlocking(@RequestBody RelationshipDto relationshipDto) {
        boolean isBlocking = socialGraphService.isBlocking(relationshipDto.fromUserId(), relationshipDto.toUserId());
        return ResponseEntity.ok(isBlocking);
    }

    @Tag(name = "Follow recommendations")
    @Operation(description = "Returns given the userId the possible follow recommendations. All scores are in range [0,1], the greater the score the better the recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DB queried successfully"),
            @ApiResponse(responseCode = "400", description = "Given id must be valid number", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<Recommendation[]> getUserRecommendations(@PathVariable String userId) {

        List<Recommendation> recommendations = socialGraphService.getUserRecommendations(userId);

        return ResponseEntity.ok(recommendations.toArray(Recommendation[]::new));

    }

    @Tag(name = "User relations")
    @Operation(description = "Returns given the userId all its social relations: followers, following, blocked, blocking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DB queried successfully"),
            @ApiResponse(responseCode = "400", description = "Given id must be valid number", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = SpringErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = SpringErrorDto.class)))
    })
    @GetMapping("/relations/{userId}")
    public ResponseEntity<UserRelations> getUserRelations(@PathVariable String userId) {

        UserRelations userRelations = socialGraphService.getUserRelations(userId);

        return ResponseEntity.ok(userRelations);
    }

}
