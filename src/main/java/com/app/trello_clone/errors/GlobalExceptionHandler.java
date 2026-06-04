package com.app.trello_clone.errors;

import com.app.trello_clone.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError>
  handleUserNotFound(UserNotFoundException e) {

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(
        new ApiError(
          "USER_NOT_FOUND",
          e.getMessage()
        )
      );
  }
}
