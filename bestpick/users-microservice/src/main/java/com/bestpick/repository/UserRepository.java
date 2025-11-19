package com.bestpick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bestpick.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
