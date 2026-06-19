package com.app.trello_clone.errors;

import com.app.trello_clone.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * ユーザ検索失敗
   * @param e
   * @return responseEntity (HTTP Status Code: 401)
   */
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

  /**
   * ユーザが既に登録済み (Emailアドレス使用済み)
   * @param e
   * @return responseEntity (HTTP Status Code: 409)
   */
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiError>
  handleUserAlreadyExists(UserAlreadyExistsException e) {
    return ResponseEntity
      .status(HttpStatus.CONFLICT)
      .body(
        new ApiError(
          "USER_ALREADY_EXISTS",
          e.getMessage()
        )
      );
  }
}
