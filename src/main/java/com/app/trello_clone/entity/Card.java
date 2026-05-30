package com.app.trello_clone.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class Card {
  private UUID id;
  private String title;
  private int position;
  private Date dueDate;
  private boolean isCompleted;
  private LocalDateTime cratedAt;
  private LocalDateTime updatedAt;
}
