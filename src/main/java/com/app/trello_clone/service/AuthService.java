package com.app.trello_clone.service;

import com.app.trello_clone.dto.SigninRequest;
import com.app.trello_clone.dto.SignupRequest;
import com.app.trello_clone.dto.AuthResponse;
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
  public AuthResponse signup(SignupRequest request) {
    authRepository.createUser(
      request.getName(),
      request.getEmail(),
      request.getPassword()
    );

    User user = authRepository.findUserByEmail(request.getEmail());
    String token = jwtService.generateToken(user);
    return new AuthResponse(user, token);
  }

  /**
   * ログイン
   * @param request
   * @return response
   */
  public AuthResponse signin(SigninRequest request) {
    User user = authRepository.findUserByEmail(request.email());
    String token = jwtService.generateToken(user);
    return new AuthResponse(user, token);
  }

  /**
   * ユーザ情報取得
   * @param userId
   * @return user
   */
  public User getMe(String userId) {
    return authRepository.findUserById(userId);
  }
}
