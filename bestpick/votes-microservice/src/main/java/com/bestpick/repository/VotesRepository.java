package com.bestpick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bestpick.model.Vote;
import com.bestpick.model.VoteId;

public interface VotesRepository extends JpaRepository<Vote, VoteId> {

}