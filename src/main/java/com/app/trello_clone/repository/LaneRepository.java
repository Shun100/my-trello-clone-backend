package com.app.trello_clone.repository;

import com.app.trello_clone.dto.lane.UpdateLaneRequest;
import com.app.trello_clone.entity.Lane;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LaneRepository {
  private final JdbcTemplate jdbcTemplate;

  /**
   * Lane新規作成
   * @param boardId
   * @param title
   * @param position
   * @return id
   */
  public UUID create(UUID boardId, String title, int position) {
    String sql = """
      INSERT INTO lanes (board_id, title, position)
      VALUES (?, ?, ?)
      RETURNING id
    """;

    return jdbcTemplate.queryForObject(
      sql,
      UUID.class,
      boardId,
      title,
      position
    );
  }

  /**
   * Lane検索 (boardId)
   * @param boardId
   * @return lanes
   */
  public List<Lane> findByBoardId (UUID boardId) {
    String sql = """
      SELECT * FROM lanes
      WHERE board_id = ?
    """;

    return jdbcTemplate.query(
      sql,
      BeanPropertyRowMapper.newInstance(Lane.class),
      boardId
    );
  }

  /**
   * Lane更新 (バッチ更新)
   * @param requests
   */
  public void update(List<UpdateLaneRequest> requests) {
    String sql = """
      UPDATE lanes
      SET
        title = ?,
        position = ?,
        updated_at = CURRENT_TIMESTAMP
      WHERE id = ?
    """;

    jdbcTemplate.batchUpdate(
      sql,
      requests,
      requests.size(),
      (ps, request) -> {
        ps.setString(1, request.getTitle());
        ps.setInt(2, request.getPosition());
        ps.setObject(3, request.getId());
      }
    );
  }
}
