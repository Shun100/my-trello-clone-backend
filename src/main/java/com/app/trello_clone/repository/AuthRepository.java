package com.app.trello_clone.repository;

import com.app.trello_clone.entity.User;
import com.app.trello_clone.repository.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
   */
  public void createUser(String name, String email, String password) {
    String sql = """
        INSERT INTO users (name, email, password)
        VALUES (?, ?, ?)
      """;
    jdbcTemplate.update(sql, name, email, password);
  }

  /**
   * ユーザ取得
   * @param email
   * @return user
   */
  public User findUserByEmail(String email) {
    String sql = """
        SELECT * FROM users WHERE email = ?
      """;
    return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
  }
}
