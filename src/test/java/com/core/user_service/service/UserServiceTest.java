package com.core.user_service.service;

import com.core.user_service.TestUtils;
import com.core.user_service.api.exception.EncryptionErrorException;
import com.core.user_service.api.exception.InvalidPasswordException;
import com.core.user_service.api.exception.UserAlreadyExistsException;
import com.core.user_service.api.exception.UserNotFoundException;
import com.core.user_service.api.model.Phone;
import com.core.user_service.api.model.User;
import com.core.user_service.api.repository.UserRepository;
import com.core.user_service.dto.PhoneDto;
import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import com.core.user_service.mapper.PhoneMapper;
import com.core.user_service.mapper.UserMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static com.core.user_service.TestConstants.CIPHER_PASSWORD;
import static com.core.user_service.TestConstants.OK_TOKEN;
import static com.core.user_service.TestConstants.TEST_INVALID_PASSWORD;
import static com.core.user_service.TestConstants.TEST_REQUEST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @MockBean
  private EncryptionService encryptionService;

  @MockBean
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  @Autowired
  private PhoneMapper phoneMapper;

  @Test
  void signUpTest() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
    NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    SignUpRequest request = TestUtils.createSignUpRequest();
    PhoneDto phoneDto = PhoneDto.builder().number(12345).cityCode(1).countryCode("XYZ").build();
    request.setPhones(List.of(phoneDto));
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
    when(this.userRepository.save(any())).thenReturn(TestUtils.createUser());
    when(this.encryptionService.encryptPassword(request.getPassword())).thenReturn(CIPHER_PASSWORD);
    when(this.tokenService.generateToken(anyString())).thenReturn(OK_TOKEN);
    UserResponse response = this.userService.signUp(request, TEST_REQUEST_ID);
    assertNotNull(response);
    assertFalse(Strings.isBlank(response.getId()));
    assertNotNull(response.getCreated());
    assertNotNull(response.getLastLogin());
    assertNotNull(response.getIsActive());
    assertFalse(Strings.isBlank(response.getIsActive()));
    assertEquals("true", response.getIsActive());
    assertFalse(Strings.isBlank(response.getToken()));
    assertEquals(response.getCreated(), response.getLastLogin());
  }

  @Test
  void signUpUserAlreadyExists() {
    SignUpRequest request = TestUtils.createSignUpRequest();
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(TestUtils.createUser()));
    assertThrows(UserAlreadyExistsException.class,
      () -> this.userService.signUp(request, TEST_REQUEST_ID));
  }

  @Test
  void signUpPasswordEncryptionError() throws InvalidAlgorithmParameterException, NoSuchPaddingException,
    IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    SignUpRequest request = TestUtils.createSignUpRequest();
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
    when(this.encryptionService.encryptPassword(anyString())).thenThrow(new InvalidKeyException());
    assertThrows(EncryptionErrorException.class,
      () -> this.userService.signUp(request, TEST_REQUEST_ID));
  }

  @Test
  void loginTest() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
    NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    LoginRequest request = TestUtils.createLoginRequest();
    User user = TestUtils.createUser();
    Phone phone = Phone.builder().number(12345).cityCode(1).countryCode("XYZ").build();
    user.setPhones(List.of(phone));
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
    when(this.encryptionService.decryptPassword(anyString())).thenReturn(request.getPassword());
    doNothing().when(this.tokenService).validateToken(request.getToken(), request.getEmail());
    when(this.userRepository.save(any())).thenReturn(user);
    LoginResponse response = this.userService.login(request, TEST_REQUEST_ID);
    assertNotNull(response);
    assertFalse(Strings.isBlank(response.getId()));
    assertNotNull(response.getCreated());
    assertNotNull(response.getLastLogin());
    assertFalse(Strings.isBlank(response.getIsActive()));
    assertEquals("true", response.getIsActive());
    assertEquals(user.getName(), response.getName());
    assertEquals(user.getEmail(), response.getEmail());
    assertEquals(user.getPassword(), response.getPassword());
    assertNotNull(response.getPhones());
    assertFalse(response.getPhones().isEmpty());
    assertEquals(user.getPhones().get(0).getNumber(), response.getPhones().get(0).getNumber());
  }

  @Test
  void loginUserNotFound() {
    LoginRequest request = TestUtils.createLoginRequest();
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class,
      () -> this.userService.login(request, TEST_REQUEST_ID));
  }

  @Test
  void loginInvalidPassword() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
    NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    LoginRequest request = TestUtils.createLoginRequest();
    User user = TestUtils.createUser();
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
    when(this.encryptionService.decryptPassword(anyString())).thenReturn(TEST_INVALID_PASSWORD);
    assertThrows(InvalidPasswordException.class,
      () -> this.userService.login(request, TEST_REQUEST_ID));
  }

  @Test
  void decryptionException() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
    NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    LoginRequest request = TestUtils.createLoginRequest();
    User user = TestUtils.createUser();
    when(this.userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
    when(this.encryptionService.decryptPassword(anyString())).thenThrow(EncryptionErrorException.class);
    assertThrows(EncryptionErrorException.class,
      () -> this.userService.login(request, TEST_REQUEST_ID));
  }

}
