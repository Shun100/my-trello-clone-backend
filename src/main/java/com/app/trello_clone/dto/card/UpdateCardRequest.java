package com.app.trello_clone.dto.card;

import com.app.trello_clone.constant.CardStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UpdateCardRequest {
  private UUID id;
  private String title;
  private CardStatus status;
  private Date dueDate;
  private String description;
}
