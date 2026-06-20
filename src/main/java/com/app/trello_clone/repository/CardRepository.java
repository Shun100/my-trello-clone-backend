package com.app.trello_clone.repository;

import com.app.trello_clone.dto.card.UpsertCardRequest;
import com.app.trello_clone.entity.Card;
import com.app.trello_clone.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CardRepository {
  private final JdbcTemplate jdbcTemplate;

  /**
   * Card新規登録
   * @param card
   * @return createdCard
   */
  public Card create(Card card) {
    String sql = """
      INSERT INTO cards (lane_id, title, position, due_date, status, description)
      VALUES (?, ?, ?, ?, ?, ?)
      RETURNING *
    """;

    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(Card.class),
      card.getLaneId(),
      card.getTitle(),
      card.getPosition(),
      card.getDueDate(),
      card.getStatus().name(),
      card.getDescription()
    );
  }

  /**
   * Card検索 (LaneId)
   * @param laneId
   * @return cards
   */
  public List<Card> findByLaneId(UUID laneId) {
    String sql = """
      SELECT * FROM cards WHERE lane_id = ?
    """;

    return jdbcTemplate.query(
      sql,
      BeanPropertyRowMapper.newInstance(Card.class),
      laneId
    );
  }

  /**
   * Card更新
   * @param card
   */
  public void update(Card card) {
    System.out.println(card.toString());

    String sql = """
      UPDATE cards
      SET
        title = ?,
        due_date = ?,
        status = ?,
        description = ?,
        updated_at = CURRENT_TIMESTAMP
      WHERE id = ?
    """;

    jdbcTemplate.update(
      sql,
      card.getTitle(),
      card.getDueDate(),
      card.getStatus().name(),
      card.getDescription(),
      card.getId()
    );

//    jdbcTemplate.batchUpdate(
//      sql,
//      requests,
//      requests.size(),
//      (ps, request) -> {
//        ps.setString(1, request.getTitle());
//        ps.setDate(2, Utils.toSqlDate(request.getDueDate()));
//        ps.setString(3, request.getStatus().name());
//        ps.setString(4, request.getDescription());
//      }
//    );
  }

  /**
   * カード削除
   * @param cardId
   */
  public void delete(UUID cardId) {
    String sql = """
      DELETE FROM cards WHERE id = ?
    """;

    jdbcTemplate.update(sql, cardId);
  }
}
