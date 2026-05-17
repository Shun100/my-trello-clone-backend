package com.app.trello_clone.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
  private String id;
  private String name;
  private String email;
  private String boardId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
