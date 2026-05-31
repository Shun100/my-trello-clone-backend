package com.app.trello_clone.dto.lane;

import lombok.Data;

import java.util.UUID;

/**
 * Lane更新用
 * 複数件一気に更新するためDTO定義にする
 */
@Data
public class UpdateLaneRequest {
  private UUID id;
  private String title;
  private int position;
}
