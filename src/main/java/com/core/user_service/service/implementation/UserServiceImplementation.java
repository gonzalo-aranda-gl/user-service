package com.core.user_service.service.implementation;

import com.core.user_service.api.exception.UserAlreadyExistsException;
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
  public UserResponse singUp(SingUpRequest request, String requestId) {
    log.info("Processing user sing-up request, requestId: {}", requestId);
    if (userAlreadyExists(request.getEmail())) {
      throw new UserAlreadyExistsException("An user for this email already exists");
    }
    return null;
  }

  @Override
  public LoginResponse login(LoginRequest request, String requestId) {
    log.info("Processing user login request, requestId: {}", requestId);
    return null;
  }

  private boolean userAlreadyExists(String email) {
    return this.userRepository.findUserByEmail(email).isPresent();
  }
}
