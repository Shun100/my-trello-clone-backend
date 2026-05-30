package com.app.trello_clone.repository.mapper;

import com.app.trello_clone.dto.UpdateGroupRequest;
import com.app.trello_clone.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GroupRepository {
  private final JdbcTemplate jdbcTemplate;

  /**
   * Group新規作成
   * @param boardId
   * @param title
   * @param position
   * @return group
   */
  public Group createGroup(UUID boardId, String title, int position) {
    String sql = """
      INSERT INTO GROUP (board_id, title, position) VALUES (?, ?, ?)
      RETURNING *
    """;

    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(Group.class),
      boardId,
      title,
      position
    );
  }

  /**
   * Group更新 (バッチ更新)
   * @param requests
   */
  public void updateGroups(List<UpdateGroupRequest> requests) {
    String sql = """
      UPDATE groups
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
