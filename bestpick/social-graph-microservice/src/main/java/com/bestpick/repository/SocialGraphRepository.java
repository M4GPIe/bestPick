package com.bestpick.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SocialGraphRepository extends Neo4jRepository<User, String> {

}
