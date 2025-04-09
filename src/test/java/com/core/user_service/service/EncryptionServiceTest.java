package com.core.user_service.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.core.user_service.TestConstants.CIPHER_PASSWORD;
import static com.core.user_service.TestConstants.TEST_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EncryptionServiceTest {

  @Autowired
  private EncryptionService encryptionService;

  @Test
  void encryptPasswordTest(){
    assertDoesNotThrow(() -> {
      String cipherPassword = this.encryptionService.encryptPassword(TEST_PASSWORD);
      assertFalse(Strings.isBlank(cipherPassword));
    } );
  }

  @Test
  void decryptPasswordTest(){
    assertDoesNotThrow(() -> {
      String password = this.encryptionService.decryptPassword(CIPHER_PASSWORD);
      assertFalse(Strings.isBlank(password));
      assertEquals(TEST_PASSWORD, password);
    } );
  }
}
