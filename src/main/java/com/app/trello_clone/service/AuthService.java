package com.app.trello_clone.service;

import com.app.trello_clone.dto.SignupRequest;
import com.app.trello_clone.dto.SignupResponse;
import com.app.trello_clone.entity.User;
import com.app.trello_clone.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository authRepository;
  private final JwtService jwtService;

  /**
   * 新規ユーザ登録
   * @param request
   * @return response
   */
  public SignupResponse signup(SignupRequest request) {
    authRepository.createUser(
      request.getName(),
      request.getEmail(),
      request.getPassword()
    );

    User user = authRepository.findUserByEmail(request.getEmail());

    String token = jwtService.generateToken(user);

    return new SignupResponse(user, token);
  }
}
