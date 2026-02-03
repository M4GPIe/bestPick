package com.bestpick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bestpick.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);

    List<User> findByUid(String uid);
}
