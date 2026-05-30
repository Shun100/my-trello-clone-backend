package com.app.trello_clone.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Group {
  private UUID id;
  private String title;
  private int position;
  private List<Card> cards;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
