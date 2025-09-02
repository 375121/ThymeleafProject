package com.pappucoder.in.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pappucoder.in.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
