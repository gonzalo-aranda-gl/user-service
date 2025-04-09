package com.core.user_service.api.exception;

public class EncryptionErrorException extends RuntimeException {
  public EncryptionErrorException(String message) {
    super(message);
  }

}
