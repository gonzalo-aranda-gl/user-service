package com.core.user_service.utils;

import com.core.user_service.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class TokenStorageTest {

  @Test
  void getTokenTest() {
    String username = "testUser@gmail.com";
    TokenStorage.saveToken(username, TestConstants.OK_TOKEN);
    String obtainedToken = TokenStorage.getToken(username);
    assertEquals(TestConstants.OK_TOKEN, obtainedToken);
  }
}
