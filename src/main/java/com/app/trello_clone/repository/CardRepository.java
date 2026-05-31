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
   * @param request
   * @return id
   */
  public UUID create(UpsertCardRequest request) {
    String sql = """
      INSERT INTO cards (lane_id, title, position, due_date, status, desc)
      VALUES (?, ?, ?, ?, ?, ?)
      RETURNING id
    """;

    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(UUID.class),
      request.getLaneId(),
      request.getTitle(),
      request.getPosition(),
      Utils.toSqlDate(request.getDueDate()),
      request.getStatus().name(),
      request.getDesc()
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
   * @param requests
   */
  public void update(List<UpsertCardRequest> requests) {
    String sql = """
      UPDATE cards
      SET
        lane_id = ?,
        title = ?,
        position = ?,
        due_date = ?,
        status = ?,
        desc = ?
    """;

    jdbcTemplate.batchUpdate(
      sql,
      requests,
      requests.size(),
      (ps, request) -> {
        ps.setObject(1, request.getLaneId());
        ps.setString(2, request.getTitle());
        ps.setInt(3, request.getPosition());
        ps.setDate(4, Utils.toSqlDate(request.getDueDate()));
        ps.setString(5, request.getStatus().name());
        ps.setString(6, request.getDesc());
      }
    );
  }
}
