package com.app.trello_clone.repository.test;

import com.app.trello_clone.entity.Lane;
import com.app.trello_clone.repository.LaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Profile("test")
public class LaneRepositoryTest implements LaneRepository {
  private final JdbcTemplate jdbcTemplate;

  @Override
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

  @Override
  public List<Lane> findByBoardId(UUID boardId) {
    String sql = """
      SELECT * FROM lanes
      WHERE board_id = ?
      ORDER BY position
    """;

    return jdbcTemplate.query(
      sql,
      BeanPropertyRowMapper.newInstance(Lane.class),
      boardId
    );
  }

  @Override
  public void update(List<Lane> lanes) {
    throw new DataAccessResourceFailureException("Failed to update");
  }

  @Override
  public void delete(UUID cardId) {
    throw new DataAccessResourceFailureException("Failed to delete");
  }
}
