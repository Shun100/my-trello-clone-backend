package com.app.trello_clone.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Board {
  private UUID id;
  private String title;
  private List<Group> groups;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
