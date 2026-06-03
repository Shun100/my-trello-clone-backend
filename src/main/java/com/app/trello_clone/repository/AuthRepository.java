package com.app.trello_clone.repository;

import com.app.trello_clone.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

  // Spring JDBC用の設定情報
  private final JdbcTemplate jdbcTemplate;

  /**
   * 新規ユーザ登録
   * @param name
   * @param email
   * @param password
   * @return userId
   */
  public UUID create(String name, String email, String password) {
    String sql = """
        INSERT INTO users (name, email, password)
        VALUES (?, ?, ?)
        RETURNING id
      """;
    return jdbcTemplate.queryForObject(
      sql,
      UUID.class,
      name,
      email,
      password
    );
  }

  /**
   * ユーザ検索 (Email)
   * @param email
   * @return user
   */
  public User findByEmail(String email) {
    String sql = """
        SELECT * FROM users WHERE email = ?
      """;
    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(User.class),
      email);
  }

  /**
   * ユーザ検索 (ユーザID)
   * @param userId
   * @return user
   */
  public User findById(UUID userId) {
    String sql = """
        SELECT * FROM users WHERE id = ?
      """;
    return jdbcTemplate.queryForObject(
      sql,
      BeanPropertyRowMapper.newInstance(User.class),
      userId
    );
  }

  /**
   * ユーザ削除
   * NOTE: Board, Group, CardはDELETE CASCADEで連動して消える
   * @param userId
   */
  public void deleteUser(UUID userId) {
    String sql = """
      DELETE FROM users WHERE id = ?
    """;
    jdbcTemplate.update(sql, userId);
  }
}
