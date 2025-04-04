package com.core.user_service.service.implementation;

import com.core.user_service.api.repository.PhoneRepository;
import com.core.user_service.api.repository.UserRepository;
import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SingUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import com.core.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final PhoneRepository phoneRepository;

  @Override
  public UserResponse singUp(SingUpRequest singUpRequest) {
    return null;
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    return null;
  }
}
