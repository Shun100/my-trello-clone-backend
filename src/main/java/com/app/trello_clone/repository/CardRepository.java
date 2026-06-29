package com.app.trello_clone.repository;

import com.app.trello_clone.entity.Card;

import java.util.List;
import java.util.UUID;

public interface CardRepository {
  /**
   * カード新規作成
   * @param card
   * @return createdCard
   */
  Card create(Card card);

  /**
   * カード検索
   * @param laneId
   * @return cards
   */
  List<Card> findByLaneId(UUID laneId);

  /**
   * カード更新
   * @param card
   */
  void update(Card card);

  /**
   * position更新
   * @param cards
   */
  void updatePosition(List<Card> cards);

  /**
   * カード削除
   * @param cardId
   */
  void delete(UUID cardId);
}
