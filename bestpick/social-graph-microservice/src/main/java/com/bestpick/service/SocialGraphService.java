package com.bestpick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestpick.model.Recommendation;
import com.bestpick.repository.SocialGraphRepository;

@Service
public class SocialGraphService {

    @Autowired
    private SocialGraphRepository socialGraphRepository;

    public void follow(String fromUserId, String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'follow'");
    }

    public void unfollow(String fromUserId, String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unfollow'");
    }

    public void block(String fromUserId, String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'block'");
    }

    public void unblock(String fromUserId, String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unblock'");
    }

    public List<Recommendation> getUserRecommendations(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRecommendations'");
    }

}
