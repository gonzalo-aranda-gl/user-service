package com.core.user_service.service.implementation;

import com.core.user_service.api.exception.EncryptionErrorException;
import com.core.user_service.api.exception.InvalidPasswordException;
import com.core.user_service.api.model.Phone;
import com.core.user_service.constants.UserStatus;
import com.core.user_service.dto.PhoneDto;
import com.core.user_service.mapper.UserMapper;
import com.core.user_service.api.exception.UserAlreadyExistsException;
import com.core.user_service.api.exception.UserNotFoundException;
import com.core.user_service.api.model.User;
import com.core.user_service.api.repository.UserRepository;
import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import com.core.user_service.service.EncryptionService;
import com.core.user_service.service.TokenService;
import com.core.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final EncryptionService encryptionService;

  private final TokenService tokenService;

  @Override
  public UserResponse signUp(SignUpRequest request, String requestId) {
    log.info("Processing user sign-up request, requestId: {}", requestId);
    if (getUser(request.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("An user for this email already exists");
    }
    User user = userMapper.mapUserRequestToEntity(request);
    setUserPhones(request, user);
    return userMapper.mapUserEntityToUserResponse(saveUser(user, requestId));
  }
  @Override
  public LoginResponse login(LoginRequest request, String requestId) {
    log.info("Processing user login request, requestId: {}", requestId);
    Optional<User> userOptional = getUser(request.getEmail());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("The user doesn't exist");
    }
    User user = userOptional.get();
    if (!getDecryptedPassword(user).equals(request.getPassword())) {
      log.error("Invalid password");
      throw new InvalidPasswordException("The provided password is incorrect");
    }
    tokenService.validateToken(request.getToken(), user.getEmail());
    LoginResponse response = userMapper.mapUserEntityToLoginResponse(updateUser(user, requestId));
    response.setPhones(getUserPhones(user));
    return response;
  }

  private Optional<User> getUser(String email) {
    return this.userRepository.findUserByEmail(email);
  }

  private void setUserPhones(SignUpRequest request, User user) {
    List<Phone> phones = new ArrayList<>();
    request.getPhones().stream().filter(p -> p.getNumber() != 0L).forEach(phoneDto -> {
      Phone phone = userMapper.mapPhoneDtoToEntity(phoneDto);
      phone.setUser(user);
      phones.add(phone);
    });
    user.setPhones(phones);
  }

  private List<PhoneDto> getUserPhones(User user) {
    List<PhoneDto> phones = new ArrayList<>();
    user.getPhones().forEach(phoneDto ->
      phones.add(userMapper.mapPhoneEntityToDto(phoneDto)));
    return phones;
  }

  private User saveUser(User user, String requestId) {
    log.info("Creating user: {}, for requestId: {}", user.getEmail(), requestId);
    user.setCreated(LocalDateTime.now());
    user.setLastLogin(user.getCreated());
    setEncryptedPassword(user);
    user.setIsActive(UserStatus.ACTIVE.getIsActive());
    return this.userRepository.save(user);
  }

  private User updateUser(User user, String requestId) {
    log.info("Updating user: {}, for requestId: {}", user.getEmail(), requestId);
    user.setLastLogin(LocalDateTime.now());
    user.setUpdated(LocalDateTime.now());
    return this.userRepository.save(user);
  }

  private void setEncryptedPassword(User user) {
    log.info("Encrypting password for user: {}", user.getEmail());
    try {
      user.setPassword(encryptionService.encryptPassword(user.getPassword()));
    } catch (Exception ex) {
      log.error("Error during password encryption: {}", ex.getMessage());
      throw new EncryptionErrorException("An error occurred when encrypting the user's password");
    }
  }

  private String getDecryptedPassword(User user) {
    log.info("Decrypting password for user: {}", user.getEmail());
    String password;
    try {
      password = encryptionService.decryptPassword(user.getPassword());
    } catch (Exception ex) {
      log.error("Error during password decryption: {}", ex.getMessage());
      throw new EncryptionErrorException("An error occurred when decrypting the user's password");
    }
    return password;
  }

}
