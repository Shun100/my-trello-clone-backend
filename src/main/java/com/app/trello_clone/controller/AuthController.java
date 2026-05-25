package com.app.trello_clone.controller;

import com.app.trello_clone.dto.SigninRequest;
import com.app.trello_clone.dto.SignupRequest;
import com.app.trello_clone.dto.AuthResponse;
import com.app.trello_clone.entity.User;
import com.app.trello_clone.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  /**
   * 新規ユーザ登録
   * @param request
   * @return responseEntity
   */
  @PostMapping("/auth/signup")
  public ResponseEntity<AuthResponse> signup(
    @Valid @RequestBody SignupRequest request) {
    AuthResponse response = authService.signup(request);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(response);
  }

  @PostMapping("/auth/signin")
  public ResponseEntity<AuthResponse> signin (
    @Valid @RequestBody SigninRequest request) {
    AuthResponse response = authService.signin(request);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(response);
  }

  @PostMapping("/auth/me")
  public ResponseEntity<User> getMe (
    @Valid @RequestBody Authentication authentication) {
    String userId = authentication.getName(); // メソッド名はgetNameだが、実際に返すのはuserId
    User response = authService.getMe(userId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(response);
  }
}
