package com.bestpick.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestpick.model.Recommendation;
import com.bestpick.model.User;
import com.bestpick.model.UserRelations;
import com.bestpick.repository.SocialGraphRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocialGraphService {

    @Autowired
    private SocialGraphRepository socialGraphRepository;

    public void follow(Long fromUserId, Long toUserId) {
        socialGraphRepository.createFollowRelation(fromUserId, toUserId);
    }

    public void unfollow(Long fromUserId, Long toUserId) {
        socialGraphRepository.deleteFollowRelation(fromUserId, toUserId);
    }

    public boolean isFollowing(Long fromUserId, Long toUserId) {
        return socialGraphRepository.isFollowing(fromUserId, toUserId);
    }

    public void block(Long fromUserId, Long toUserId) {
        boolean isFollowing = socialGraphRepository.isFollowing(fromUserId, toUserId);
        if (isFollowing) {
            socialGraphRepository.deleteFollowRelation(fromUserId, toUserId);
        }
        socialGraphRepository.createBlockingRelation(fromUserId, toUserId);
    }

    public void unblock(Long fromUserId, Long toUserId) {
        socialGraphRepository.deleteBlockingRelation(fromUserId, toUserId);
    }

    public boolean isBlocking(Long fromUserId, Long toUserId) {
        return socialGraphRepository.isBlocking(fromUserId, toUserId);
    }

    public UserRelations getUserRelations(Long userId) {
        List<Long> followingIds = socialGraphRepository.getFollowingIds(userId);
        List<Long> followerIds = socialGraphRepository.getFollowerIds(userId);
        List<Long> blockingIds = socialGraphRepository.getBlockingIds(userId);
        List<Long> blockedByIds = socialGraphRepository.getBlockedByIds(userId);

        return new UserRelations(
                userId,
                Set.of(followingIds.toArray(Long[]::new)),
                Set.of(followerIds.toArray(Long[]::new)),
                Set.of(blockingIds.toArray(Long[]::new)),
                Set.of(blockedByIds.toArray(Long[]::new)));
    }

    public List<Recommendation> getUserRecommendations(Long userId) {

        // score is length of the path on the graph, so higher is worse
        // need to revert that and normalize with 0 to 1 affinity values
        List<Recommendation> recommendations = socialGraphRepository.getPossibleFriendsRecommendation(userId, 3);

        double maxPathLength = recommendations.stream().mapToDouble(Recommendation::getScore).max().orElse(1);

        // min score 1/maxPathLength
        recommendations = recommendations.stream().map(r -> {
            r.setScore((maxPathLength + 1 - r.getScore()) / maxPathLength);
            return r;
        }).toList();

        return recommendations;
    }

    public void createUserNode(User user) {
        if (user != null) {
            socialGraphRepository.save(user);
        }
    }

    public void deleteUserNode(User user) {
        socialGraphRepository.deleteUserAndRelations(user.getId());
    }
}
