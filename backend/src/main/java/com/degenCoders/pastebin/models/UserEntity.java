package com.degenCoders.pastebin.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "Users")
public class UserEntity {
    
    @Id
    private String userId;
    private String username;
    private String password;
    private String email;
    private Instant createdAt;
    
    // Constructors, getters, and setters
    
    public UserEntity() {
        // Default constructor
    }

    public UserEntity(String userId, String username, String password, String email, Instant createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

