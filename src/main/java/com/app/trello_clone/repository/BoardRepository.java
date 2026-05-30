package com.app.trello_clone.repository;

import com.app.trello_clone.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@RequiredArgsConstructor
public class BoardRepository {
  private final JdbcTemplate jdbcTemplate;

  /**
   * Board新規作成
   * @param userId
   * @param title
   * @return board ※ List<Group> groups はnullなので注意
   */
  public Board createBoard(UUID userId, String title) {
    String sql = """
      INSERT INTO boards (user_id) VALUES (?);
      RETURNING *;
    """;
    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(Board.class),
      userId,
      title
    );
  }

  /**
   * Board取得
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
