package com.app.trello_clone.errors;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String email) {
    super("already exists: " + email);
  }
}
