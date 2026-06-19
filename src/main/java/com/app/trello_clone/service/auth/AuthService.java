package com.app.trello_clone.service.auth;

import com.app.trello_clone.dto.auth.SigninRequest;
import com.app.trello_clone.dto.auth.SignupRequest;
import com.app.trello_clone.dto.auth.AuthResponse;
import com.app.trello_clone.entity.User;
import com.app.trello_clone.errors.UserAlreadyExistsException;
import com.app.trello_clone.errors.UserNotFoundException;
import com.app.trello_clone.repository.AuthRepository;
import com.app.trello_clone.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository userRepository;
  private final BoardService boardService;
  private final JwtService jwtService;

  /**
   * signup
   * @param request
   * @return response
   */
  public AuthResponse signup(SignupRequest request) {
    String email = request.getEmail();
    UUID userId = null;

    try {
      // ユーザ登録
      userId = userRepository.create(
        request.getName(),
        email,
        request.getPassword()
      );
    } catch (DuplicateKeyException e) {
      throw new UserAlreadyExistsException(e.getMessage());
    }

    // Sample board作成
    boardService.create(userId);

    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      String token = jwtService.generateToken(user);
      return new AuthResponse(user, token);
    } else {
      throw new UserNotFoundException(userId);
    }
  }

  /**
   * signin
   * @param request
   * @return response
   */
  public AuthResponse signin(SigninRequest request) {
    User user = userRepository.findByEmail(request.getEmail());
    String token = jwtService.generateToken(user);
    return new AuthResponse(user, token);
  }

  /**
   * ユーザ情報取得
   * @param userId
   * @return user
   */
  public User find(UUID userId) {
    return userRepository
      .findById(userId)
      .orElseThrow(() -> new UserNotFoundException(userId));
  }
}
