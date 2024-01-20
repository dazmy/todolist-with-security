package com.dazmy.todolist.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(String username);

    boolean isTokenValid(String token, UserDetails userDetails);

    String getUsername();
}
