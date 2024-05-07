package com.degenCoders.pastebin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.degenCoders.pastebin.models.UserEntity;
import com.degenCoders.pastebin.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(String userId, UserEntity user) {
        user.setUserId(userId);
        return userRepository.save(user);
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
}