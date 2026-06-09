package com.app.trello_clone.entity;

import com.app.trello_clone.constant.CardStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class Card {
  private UUID id;
  private UUID laneId;
  private String title;
  private int position;
  private Date dueDate;
  private CardStatus status;
  private String description;
  private LocalDateTime cratedAt;
  private LocalDateTime updatedAt;
}
