package com.app.trello_clone.repository;

import com.app.trello_clone.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
  private final JdbcTemplate jdbcTemplate;

  /**
   * Board新規作成
   * @param userId
   * @param title
   * @return boardId
   */
  public UUID create(UUID userId, String title) {
    String sql = """
      INSERT INTO boards (user_id, title) VALUES (?, ?)
      returning id
    """;

    return jdbcTemplate.queryForObject(
      sql,
      UUID.class,
      userId,
      title
    );
  }

  /**
   * Board検索 (userId)
   * @param userId
   * @return board
   */
  public Board findByUserId(UUID userId) {
    String sql = """
      SELECT * FROM boards WHERE user_id = ?;
    """;

    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(Board.class),
      userId
    );
  }
}
