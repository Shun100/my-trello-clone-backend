package com.app.trello_clone.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Lane {
  private UUID id;
  private UUID boardId;
  private String title;
  private int position;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
