package com.core.user_service.api.exception.handler;

import com.core.user_service.api.exception.InvalidPasswordException;
import com.core.user_service.api.exception.InvalidTokenException;
import com.core.user_service.api.exception.UserAlreadyExistsException;
import com.core.user_service.api.exception.UserNotFoundException;
import com.core.user_service.dto.responses.Error;
import com.core.user_service.dto.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String HEADER_REQUEST_ID = "requestId";

  public static final String REQUEST_CANNOT_BE_PROCESSED_DEFAULT_MESSAGE = "Request cannot be processed, requestId: {}.";

  @Override
  protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                         @NotNull HttpHeaders headers,
                                                                         @NotNull HttpStatus  status,
                                                                         WebRequest request) {
    log.error("Handling MethodArgumentNotValidException with message: {}", ex.getMessage());
    final String requestId = request.getHeader(HEADER_REQUEST_ID);
    log.error(REQUEST_CANNOT_BE_PROCESSED_DEFAULT_MESSAGE, requestId, ex);
    final List<String> errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      buildErrorResponse(HttpStatus.BAD_REQUEST.value(), errors)
    );
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  protected ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
    log.error("Handling UserAlreadyExistsException with message: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      buildErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()))
    );
  }

  @ExceptionHandler(UserNotFoundException.class)
  protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("Handling UserNotFoundException with message: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
      buildErrorResponse(HttpStatus.NO_CONTENT.value(), List.of(ex.getMessage()))
    );
  }

  @ExceptionHandler(InvalidPasswordException.class)
  protected ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex) {
    log.error("Handling InvalidPasswordException with message: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            buildErrorResponse(HttpStatus.UNAUTHORIZED.value(), List.of(ex.getMessage()))
    );
  }

  @ExceptionHandler(InvalidTokenException.class)
  protected ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
    log.error("Handling InvalidTokenException with message: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            buildErrorResponse(HttpStatus.FORBIDDEN.value(), List.of(ex.getMessage()))
    );
  }

  private ErrorResponse buildErrorResponse(int httpStatusCode, List<String> message) {
    List<Error> errors = new ArrayList<>();
    message.forEach(error -> errors.add(Error.builder()
      .timestamp(LocalDateTime.now())
      .code(httpStatusCode)
      .detail(error)
      .build()));
    return ErrorResponse.builder().errors(errors).build();
  }
}
