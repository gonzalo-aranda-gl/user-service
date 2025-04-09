package com.core.user_service.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
@Slf4j
public class TokenStorage {

  private final Map<String, String> tokenVault = new ConcurrentHashMap<>();

  public void saveToken(String username, String token) {
    tokenVault.put(username, token);
  }

  public String getToken(String username) {
    return tokenVault.get(username);
  }
}
