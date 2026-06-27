package com.app.trello_clone.repository;

import com.app.trello_clone.entity.Lane;

import java.util.List;
import java.util.UUID;

public interface LaneRepository {
  // Lane新規作成
  Lane create(UUID boardId, String title, int position);

  // Lane検索
  List<Lane> findByBoardId (UUID boardId);

  // Lane更新
  void update(List<Lane> lanes);

  // Lane削除
  void delete(UUID id);
}
