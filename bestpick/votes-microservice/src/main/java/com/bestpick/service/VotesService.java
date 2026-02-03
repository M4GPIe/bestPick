package com.bestpick.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.model.Vote;
import com.bestpick.model.VoteId;
import com.bestpick.model.VoteMetadata;
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

        VoteMetadata metadata = new VoteMetadata(Instant.now(), Instant.now());
        vote.setMetadata(metadata);

        return votesRepository.save(vote);
    }

    public List<Vote> getVotes(Long userId, String postId, VoteType type) {

        List<Vote> votes = votesRepository.getVotes(userId, postId, type);

        return votes;
    }

    public Vote updateVote(Long userId, String postId, Vote entity) {
        VoteId id = new VoteId(userId, postId);

        Vote preVote = votesRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The vote you are updating doesn't exist"));

        preVote.setVote(entity.getVote());

        VoteMetadata newMetadata = new VoteMetadata(
                preVote.getMetadata().getCreationDate(),
                Instant.now());

        preVote.setMetadata(newMetadata);

        votesRepository.save(preVote);

        return preVote;
    }

    public void deleteVote(Long userId, String postId) {

        VoteId id = new VoteId(userId, postId);

        if (!votesRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The vote you are updating doesn't exist");
        }

        votesRepository.deleteById(id);

    }

}
