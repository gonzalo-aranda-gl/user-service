package com.core.user_service.service;

public interface TokenService {

    String generateToken(String username);

    void validateToken(String token, String username);
}
