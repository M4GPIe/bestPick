package com.bestpick.service;

import java.util.List;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRelations'");
    }

    public List<Recommendation> getUserRecommendations(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRecommendations'");
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
