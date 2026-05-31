package com.app.trello_clone.service.auth;

import com.app.trello_clone.dto.auth.SigninRequest;
import com.app.trello_clone.dto.auth.SignupRequest;
import com.app.trello_clone.dto.auth.AuthResponse;
import com.app.trello_clone.entity.User;
import com.app.trello_clone.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository userRepository;
  private final JwtService jwtService;

  /**
   * signup
   * @param request
   * @return response
   */
  public AuthResponse signup(SignupRequest request) {
    UUID userId = userRepository.create(
      request.getName(),
      request.getEmail(),
      request.getPassword()
    );

    User user = userRepository.findById(userId);
    String token = jwtService.generateToken(user);
    return new AuthResponse(userId, token);
  }

  /**
   * signin
   * @param request
   * @return response
   */
  public AuthResponse signin(SigninRequest request) {
    User user = userRepository.findByEmail(request.getEmail());
    String token = jwtService.generateToken(user);
    return new AuthResponse(user.getId(), token);
  }

  /**
   * ユーザ情報取得
   * @param userId
   * @return user
   */
  public User getMe(String userId) {
    UUID uuid = UUID.fromString(userId);
    return userRepository.findById(uuid);
  }
}
