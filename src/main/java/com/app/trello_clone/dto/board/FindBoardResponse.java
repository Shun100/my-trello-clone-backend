package com.app.trello_clone.dto.board;

import com.app.trello_clone.dto.lane.FindLaneResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class FindBoardResponse {
  private UUID id;
  private String title;
  private List<FindLaneResponse> lanes;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
