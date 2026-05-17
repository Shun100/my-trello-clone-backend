package com.app.trello_clone.dto;

import com.app.trello_clone.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {
  private User user;
  private String token;
}
