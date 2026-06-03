package com.app.trello_clone.dto.board;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateBoardRequest {
  private UUID userId;
}
