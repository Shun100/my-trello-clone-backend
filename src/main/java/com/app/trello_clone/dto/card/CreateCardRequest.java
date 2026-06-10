package com.app.trello_clone.dto.card;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCardRequest {
  private String title;
  private UUID laneId;
  private int position;
}
