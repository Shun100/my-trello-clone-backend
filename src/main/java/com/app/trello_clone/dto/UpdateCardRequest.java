package com.app.trello_clone.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Card更新用
 * 複数件一気に更新するためDTO定義にする
 */
@Data
public class UpdateCardRequest {
  private UUID id;
  private String title;
  private int position;
  private Date dueDate;
  private boolean isCompleted;
}
