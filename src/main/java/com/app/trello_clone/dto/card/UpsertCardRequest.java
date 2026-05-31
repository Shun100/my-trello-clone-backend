package com.app.trello_clone.dto.card;

import com.app.trello_clone.constant.CardStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UpsertCardRequest {
  private UUID laneId;
  private String title;
  private int position;;
  private Date dueDate;;
  private CardStatus status;
  private String description;
}
