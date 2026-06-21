package com.app.trello_clone.repository.test;

import com.app.trello_clone.entity.Card;
import com.app.trello_clone.repository.CardRepository;
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
public class CardRepositoryTest implements CardRepository {
  private final JdbcTemplate jdbcTemplate;

  @Override
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
   * カード検索
   * @param laneId
   * @return cards
   */
  @Override
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
   * カード更新
   * @param card
   */
  @Override
  public void update(Card card) {
    throw new DataAccessResourceFailureException("Failed to update");
  }

  /**
   * カード削除
   * @param cardId
   */
  @Override
  public void delete(UUID cardId) {
    throw new DataAccessResourceFailureException("Failed to delete");
  }
}
