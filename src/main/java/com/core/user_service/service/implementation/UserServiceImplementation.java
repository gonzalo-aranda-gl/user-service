package com.core.user_service.service.implementation;

import com.core.user_service.api.exception.EncryptionErrorException;
import com.core.user_service.api.exception.InvalidPasswordException;
import com.core.user_service.api.model.Phone;
import com.core.user_service.constants.UserStatus;
import com.core.user_service.dto.PhoneDto;
import com.core.user_service.dto.UserDto;
import com.core.user_service.mapper.PhoneMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final PhoneMapper phoneMapper;

  private final EncryptionService encryptionService;

  private final TokenService tokenService;

  @Override
  @Transactional
  public UserResponse signUp(SignUpRequest request, String requestId) {
    log.info("Processing user sign-up request, requestId: {}", requestId);
    if (getUser(request.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("An user for this email already exists");
    }
    User user = this.userMapper.mapSignUpRequestToUserEntity(request);
    if (!Objects.isNull(request.getPhones()) && !request.getPhones().isEmpty()) {
      setUserPhones(request, user);
    }
    UserDto userDto = saveUser(user, requestId);
    UserResponse response =  this.userMapper.mapUserDtoToUserResponse(userDto);
    response.setToken(this.tokenService.generateToken(user.getEmail()));
    return response;
  }
  @Override
  @Transactional
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
    this.tokenService.validateToken(request.getToken(), user.getEmail());
    UserDto userDto = updateUser(user, requestId);
    LoginResponse response = this.userMapper.mapUserDtoToLoginResponse(userDto);
    response.setToken(this.tokenService.generateToken(user.getEmail()));
    return response;
  }

  private Optional<User> getUser(String email) {
    return this.userRepository.findUserByEmail(email);
  }

  private void setUserPhones(SignUpRequest request, User user) {
    List<Phone> phones = new ArrayList<>();
    request.getPhones().stream().filter(p -> p.getNumber() != 0L)
      .forEach(phoneDto -> {
        Phone phone = this.phoneMapper.mapPhoneDtoToEntity(phoneDto);
        phone.setUser(user);
        phones.add(phone);
    });
    user.setPhones(phones);
  }

  private List<PhoneDto> getUserPhones(User user) {
    List<PhoneDto> phones = new ArrayList<>();
    user.getPhones().forEach(phoneDto ->
      phones.add(this.phoneMapper.mapPhoneEntityToDto(phoneDto)));
    return phones;
  }

  private UserDto saveUser(User user, String requestId) {
    log.info("Creating user: {}, for requestId: {}", user.getEmail(), requestId);
    user.setCreated(LocalDateTime.now());
    user.setLastLogin(user.getCreated());
    setEncryptedPassword(user);
    user.setIsActive(UserStatus.ACTIVE.getIsActive());
    User savedUser = this.userRepository.save(user);
    return this.userMapper.mapUserEntityToUserDto(savedUser);
  }

  private UserDto updateUser(User user, String requestId) {
    log.info("Updating user: {}, for requestId: {}", user.getEmail(), requestId);
    user.setLastLogin(LocalDateTime.now());
    user.setUpdated(LocalDateTime.now());
    User updatedUser = this.userRepository.save(user);
    UserDto userDto = this.userMapper.mapUserEntityToUserDto(updatedUser);
    if (!Objects.isNull(user.getPhones()) && !user.getPhones().isEmpty()) {
      userDto.setPhones(getUserPhones(updatedUser));
    }
    return userDto;
  }

  private void setEncryptedPassword(User user) {
    log.info("Encrypting password for user: {}", user.getEmail());
    try {
      user.setPassword(this.encryptionService.encryptPassword(user.getPassword()));
    } catch (Exception ex) {
      log.error("Error during password encryption: {}", ex.getMessage());
      throw new EncryptionErrorException("An error occurred when encrypting the user's password");
    }
  }

  private String getDecryptedPassword(User user) {
    log.info("Decrypting password for user: {}", user.getEmail());
    String password;
    try {
      password = this.encryptionService.decryptPassword(user.getPassword());
    } catch (Exception ex) {
      log.error("Error during password decryption: {}", ex.getMessage());
      throw new EncryptionErrorException("An error occurred when decrypting the user's password");
    }
    return password;
  }

}
