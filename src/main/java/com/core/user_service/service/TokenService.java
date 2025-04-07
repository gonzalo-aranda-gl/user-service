package com.core.user_service.service;

public interface TokenService {

    String generateToken(String username);

    boolean validateToken(String token, String username);
}
