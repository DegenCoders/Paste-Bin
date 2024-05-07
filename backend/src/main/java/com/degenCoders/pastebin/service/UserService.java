package com.degenCoders.pastebin.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.degenCoders.pastebin.models.UserEntity;

public interface UserService {
    UserDetailsService userDetailsService();
    
    List<UserEntity> getAllUsers();

    UserEntity getUserById(String userId);

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(String userId, UserEntity user);

    void deleteUserById(String userId);

}