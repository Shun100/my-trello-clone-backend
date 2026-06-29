package com.app.trello_clone.dto.card;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCardPositionRequest {
  private UUID cardId;
  private UUID laneId;
  private int position;
}
