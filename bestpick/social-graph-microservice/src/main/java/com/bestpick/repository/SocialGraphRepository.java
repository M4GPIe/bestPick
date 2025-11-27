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
            MATCH (:User {id: $fromUserId})-[r:FOLLOWS]->(:User {id: $toUserId})
            DELETE r
            """)
    void deleteFollowRelation(Long fromUserId, Long toUserId);

    @Query("""
                RETURN EXISTS {(:User {id: $fromUserId})-[:FOLLOWS]->(:User {id: $toUserId})}
            """)
    boolean isFollowing(Long fromUserId, Long toUserId);

    @Query("""
                MERGE (u1:User {id: $fromUserId})
                MERGE (u2:User {id: $toUserId})
                MERGE (u1)-[:BLOCKS]->(u2)
            """)
    void createBlockingRelation(Long fromUserId, Long toUserId);

    @Query("""
            MATCH (: User {id: $fromUserId})-[r:BLOCKS]->(: User {id: $toUserId})
            DELETE r
            """)
    void deleteBlockingRelation(Long fromUserId, Long toUserId);

    @Query("""
            RETURN EXISTS {(:User {id: $fromUserId})-[:BLOCKS]->(:User {id: $toUserId})}
            """)
    boolean isBlocking(Long fromUserId, Long toUserId);

    @Query("""
                MATCH (u: User {id: $userId})
                DETACH DELETE u
            """)
    void deleteUserAndRelations(Long userId);
}
