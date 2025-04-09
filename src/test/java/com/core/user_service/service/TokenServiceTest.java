package com.core.user_service.service;

import com.core.user_service.api.exception.InvalidTokenException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.core.user_service.TestConstants.INVALID_TOKEN;
import static com.core.user_service.TestConstants.TEST_USER_EMAIL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TokenServiceTest {

  @Autowired
  private TokenService tokenService;

  @Test
  void generateTokenTest() {
    String token = tokenService.generateToken(TEST_USER_EMAIL);
    assertFalse(Strings.isBlank(token));
    assertEquals(TEST_USER_EMAIL, tokenService.extractUsername(token));
  }

  @Test
  void validateTokenTest() {
    String token = tokenService.generateToken(TEST_USER_EMAIL);
    assertDoesNotThrow(() -> tokenService.validateToken(token, TEST_USER_EMAIL));
  }

  @Test
  void validateTokenErrorTest() {
    assertThrows(InvalidTokenException.class,
      () -> tokenService.validateToken(INVALID_TOKEN, TEST_USER_EMAIL));
  }
}
