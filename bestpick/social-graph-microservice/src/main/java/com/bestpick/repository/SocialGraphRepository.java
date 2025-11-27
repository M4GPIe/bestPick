package com.bestpick.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.bestpick.model.User;

public interface SocialGraphRepository extends Neo4jRepository<User, Long> {

    @Query("""
            MERGE (u1:User {id: $fromUserId})
            MERGE (u2:User {id: $toUserId})
            MERGE (u1)-[:FOLLOWS]->(u2)
                """)
    void createFollowRelation(Long fromUserId, Long toUserId);

    @Query("""
            MATCH (u: User {id: $userId})
            DETACH DELETE u
            """)
    void deleteUserAndRelations(Long userId);

}
