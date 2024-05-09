package com.degenCoders.pastebin.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.degenCoders.pastebin.models.UserEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>  {
    Optional<UserEntity> findByUsername(String username);
}
