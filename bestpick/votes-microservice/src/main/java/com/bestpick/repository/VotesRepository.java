package com.bestpick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bestpick.model.Vote;
import com.bestpick.model.VoteId;
import com.bestpick.model.VoteType;

public interface VotesRepository extends JpaRepository<Vote, VoteId> {

    @Query("""
            SELECT v
            FROM Vote v
            WHERE (:userId IS NULL OR v.user.id = :userId)
                AND (:postId IS NULL OR v.postId = :postId)
                AND (
                    :type IS NULL
                    OR (:type = com.bestpick.model.VoteType.POSITIVE AND v.vote>0)
                    OR (:type = com.bestpick.model.VoteType.NEGATIVE AND v.vote<0)
                )
            """)
    List<Vote> getVotes(Long userId, String postId, VoteType type);

}