package com.app.trello_clone.controller;

import com.app.trello_clone.dto.auth.SigninRequest;
import com.app.trello_clone.dto.auth.SignupRequest;
import com.app.trello_clone.dto.auth.AuthResponse;
import com.app.trello_clone.entity.User;
import com.app.trello_clone.errors.UserAlreadyExistsException;
import com.app.trello_clone.errors.UserNotFoundException;
import com.app.trello_clone.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  /**
   * signup
   * @param request
   * @return responseEntity
   */
  @PostMapping("/auth/signup")
  public ResponseEntity<AuthResponse> signup(
    @Valid @RequestBody SignupRequest request) {

    try {
      AuthResponse response = authService.signup(request);
      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);

    } catch (UserAlreadyExistsException e) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(null);
    }
  }

  /**
   * signin
   * @param request
   * @return responseEntity
   */
  @PostMapping("/auth/signin")
  public ResponseEntity<AuthResponse> signin (
    @Valid @RequestBody SigninRequest request) {
    AuthResponse response = authService.signin(request);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(response);
  }

  /**
   * 現在のユーザ情報取得
   * @param authentication
   * @return responseEntity
   */
  @GetMapping("/auth/me")
  public ResponseEntity<User> getMe (
    @Valid Authentication authentication) {

    // メソッド名はgetNameだが、実際に返すのはuserId
    UUID userId = UUID.fromString(authentication.getName());

    User response = authService.find(userId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(response);
  }
}
