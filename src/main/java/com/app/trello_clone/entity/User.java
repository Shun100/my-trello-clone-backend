package com.app.trello_clone.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
  private UUID id;
  private String name;
  private String email;
  private String boardId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
