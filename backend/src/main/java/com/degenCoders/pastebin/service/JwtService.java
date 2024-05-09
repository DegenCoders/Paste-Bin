package com.degenCoders.pastebin.service;

import com.degenCoders.pastebin.models.UserEntity;

public interface JwtService {
    String extractData(String token);
    String extractEmail(String token);
    String extractUserID(String token);
    String generateToken(UserEntity userDetails);
    boolean isTokenValid(String token, String name);
}
