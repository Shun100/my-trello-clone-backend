package com.app.trello_clone.dto.auth;

import com.app.trello_clone.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthResponse {
  private User user;
  private String token;
}
