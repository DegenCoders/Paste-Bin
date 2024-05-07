package com.degenCoders.pastebin.service;

import com.degenCoders.pastebin.dao.request.SignUpRequest;
import com.degenCoders.pastebin.dao.request.SignInRequest;
import com.degenCoders.pastebin.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}