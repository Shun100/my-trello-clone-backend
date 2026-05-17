package com.app.trello_clone.controller;

import com.app.trello_clone.dto.SignupRequest;
import com.app.trello_clone.dto.SignupResponse;
import com.app.trello_clone.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<SignupResponse> signup(
    @Valid @RequestBody SignupRequest request) {
    SignupResponse response = authService.signup(request);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(response);
  }
}
