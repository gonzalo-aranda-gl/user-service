package com.core.user_service.service.implementation;

import com.core.user_service.api.exception.EncryptionErrorException;
import com.core.user_service.api.model.Phone;
import com.core.user_service.mapper.UserMapper;
import com.core.user_service.api.exception.UserAlreadyExistsException;
import com.core.user_service.api.exception.UserNotFoundException;
import com.core.user_service.api.model.User;
import com.core.user_service.api.repository.PhoneRepository;
import com.core.user_service.api.repository.UserRepository;
import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import com.core.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final PhoneRepository phoneRepository;

  private final UserMapper userMapper;

  private final EncryptionServiceImplementation encryptionService;

  @Override
  public UserResponse signUp(SignUpRequest request, String requestId) {
    log.info("Processing user sign-up request, requestId: {}", requestId);
    if (userAlreadyExists(request.getEmail())) {
      throw new UserAlreadyExistsException("An user for this email already exists");
    }
    User user = userMapper.mapUserRequestToEntity(request);
    setUserPhones(request, user);
    UserResponse response = userMapper.mapUserEntityToUserResponse(saveUser(user));
    return response;
  }

  @Override
  public LoginResponse login(LoginRequest request, String requestId) {
    log.info("Processing user login request, requestId: {}", requestId);
    if (!userAlreadyExists(request.getEmail())) {
      throw new UserNotFoundException("The user doesn't exist");
    }
    return null;
  }

  private boolean userAlreadyExists(String email) {
    return this.userRepository.findUserByEmail(email).isPresent();
  }

  private void setUserPhones(SignUpRequest request, User user) {
    List<Phone> phones = new ArrayList<>();
    request.getPhones().forEach(phoneDto -> {
      Phone phone = userMapper.mapPhoneDtoToEntity(phoneDto);
      phone.setUser(user);
      phones.add(phone);
    });
    user.setPhones(phones);
  }

  private User saveUser(User user) {
    user.setCreated(LocalDateTime.now());
    try {
      user.setPassword(encryptionService.encryptPassword(user.getPassword()));
    } catch (Exception ex) {
      log.error("Error during password encryption: {}", ex.getMessage());
      throw new EncryptionErrorException("An error occurred when encrypting the user's password");
    }
    return this.userRepository.save(user);
  }
}
