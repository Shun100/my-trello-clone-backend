package com.app.trello_clone.dto.lane;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateLaneRequest {
  private UUID boardId;
  private String title;
  private int position;
}
