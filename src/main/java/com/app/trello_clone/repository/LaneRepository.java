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
  public Lane create(UUID boardId, String title, int position) {
    String sql = """
      INSERT INTO lanes (board_id, title, position)
      VALUES (?, ?, ?)
      RETURNING *
    """;

    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(Lane.class),
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
   * レーン更新 (バッチ更新)
   * @param lanes
   */
  public void update(List<Lane> lanes) {
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
      lanes,
      lanes.size(),
      (ps, lane) -> {
        ps.setString(1, lane.getTitle());
        ps.setInt(2, lane.getPosition());
        ps.setObject(3, lane.getId());
      }
    );
  }

  /**
   * レーン削除
   * @param id
   */
  public void delete(UUID id) {
    String sql = """
      DELETE FROM lanes
      WHERE id = ?
    """;

    jdbcTemplate.update(sql, id);
  }
}
