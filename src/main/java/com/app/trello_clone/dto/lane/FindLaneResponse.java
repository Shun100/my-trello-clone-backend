package com.app.trello_clone.dto.lane;

import com.app.trello_clone.dto.card.FindCardResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FindLaneResponse {
  private UUID id;
  private String title;
  private int position;
  private List<FindCardResponse> cards = new ArrayList<>();
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
