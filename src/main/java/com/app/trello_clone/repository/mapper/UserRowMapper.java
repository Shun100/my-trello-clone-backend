package com.app.trello_clone.repository.mapper;

import com.app.trello_clone.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    User user = new User();

    user.setId(rs.getObject("id", UUID.class));
    user.setName(rs.getString("name"));
    user.setEmail(rs.getString("email"));
    user.setBoardId(rs.getString("board_id"));
    user.setCreatedAt(
      rs.getTimestamp("created_at").toLocalDateTime()
    );
    user.setUpdatedAt(
      rs.getTimestamp("updated_at").toLocalDateTime()
    );

    return user;
  }
}
