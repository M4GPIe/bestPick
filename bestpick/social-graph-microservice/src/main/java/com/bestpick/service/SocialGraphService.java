package com.bestpick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestpick.model.Recommendation;
import com.bestpick.model.UserRelations;
import com.bestpick.repository.SocialGraphRepository;

@Service
public class SocialGraphService {

    @Autowired
    private SocialGraphRepository socialGraphRepository;

    public void follow(String fromUserId, String toUserId) {
        socialGraphRepository.createFollowRelation(fromUserId, toUserId);
    }

    public void unfollow(String fromUserId, String toUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unfollow'");
    }

    public void block(String fromUserId, String toUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'block'");
    }

    public void unblock(String fromUserId, String toUserId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unblock'");
    }

    public UserRelations getUserRelations(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRelations'");
    }

    public List<Recommendation> getUserRecommendations(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRecommendations'");
    }
}
