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

    public void block(Long fromUserId, Long toUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'block'");
    }

    public void unblock(Long fromUserId, Long toUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unblock'");
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
