package com.app.trello_clone.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Group更新用
 * 複数件一気に更新するためDTO定義にする
 */
@Data
public class UpdateGroupRequest {
  private UUID id;
  private String title;
  private int position;
}
