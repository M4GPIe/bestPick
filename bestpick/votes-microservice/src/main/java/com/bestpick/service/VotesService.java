package com.bestpick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.model.Vote;
import com.bestpick.model.VoteType;
import com.bestpick.repository.VotesRepository;

@Service
public class VotesService {

    @Autowired
    VotesRepository votesRepository;

    public Vote createVote(Vote vote) {

        if (vote == null || !Vote.isValid(vote)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }

        return votesRepository.save(vote);
    }

    public List<Vote> getVotes(Long userId, String postId, VoteType type) {

        List<Vote> votes = votesRepository.getVotes(userId, postId, type);

        return votes;
    }

    public Vote updateVote(String userId, String postId, Vote entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateVote'");
    }

    public void deleteVote(String userId, String postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteVote'");
    }

}
