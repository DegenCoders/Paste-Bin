package com.degenCoders.pastebin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.degenCoders.pastebin.models.UserEntity;
import com.degenCoders.pastebin.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserEntity getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public UserEntity updateUser(@PathVariable String userId, @RequestBody UserEntity user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
    }
}
