package com.core.user_service.api.exception.handler;

import com.core.user_service.api.exception.UserAlreadyExistsException;
import com.core.user_service.dto.responses.Error;
import com.core.user_service.dto.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String REQUEST_CANNOT_BE_PROCESSED_DEFAULT_MESSAGE = "Request cannot be processed, requestId: {}.";

  @ExceptionHandler(UserAlreadyExistsException.class)
  protected ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
    log.error("Handling UserAlreadyExistsException with message: {}", ex.getMessage());
    Error error = Error.builder()
      .timestamp(LocalDateTime.now())
      .code(HttpStatus.BAD_REQUEST.value())
      .detail(ex.getMessage())
      .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ErrorResponse.builder()
          .errors(List.of(error))
        .build()
    );
  }
}
