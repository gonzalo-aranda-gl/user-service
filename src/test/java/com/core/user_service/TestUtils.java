package com.core.user_service;

import com.core.user_service.api.model.User;
import com.core.user_service.dto.UserDto;
import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SignUpRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.core.user_service.TestConstants.OK_TOKEN;
import static com.core.user_service.TestConstants.TEST_PASSWORD;
import static com.core.user_service.TestConstants.TEST_USER_EMAIL;
import static com.core.user_service.TestConstants.TEST_USER_NAME;

@UtilityClass
public class TestUtils {

  public User createUser() {
    LocalDateTime creationTime = LocalDateTime.now();
    return User.builder()
      .id(UUID.randomUUID().toString())
      .name(TEST_USER_NAME)
      .email(TEST_USER_EMAIL)
      .password(TEST_PASSWORD)
      .created(creationTime)
      .lastLogin(creationTime)
      .isActive("true")
      .build();
  }

  public UserDto createUserDto() {
    LocalDateTime creationTime = LocalDateTime.now();
    return UserDto.builder()
      .id(UUID.randomUUID().toString())
      .name(TEST_USER_NAME)
      .email(TEST_USER_EMAIL)
      .password(TEST_PASSWORD)
      .created(creationTime)
      .lastLogin(creationTime)
      .isActive("true")
      .build();
  }

  public SignUpRequest createSignUpRequest() {
    return SignUpRequest.builder()
      .name(TEST_USER_NAME)
      .email(TEST_USER_EMAIL)
      .password(TEST_PASSWORD)
      .build();
  }

  public LoginRequest createLoginRequest() {
    return LoginRequest.builder()
      .email(TEST_USER_EMAIL)
      .password(TEST_PASSWORD)
      .token(OK_TOKEN)
      .build();
  }
}
