package com.app.trello_clone.service;

import com.app.trello_clone.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expirationMs;

  private Key key;

  /**
   * HS256用の秘密鍵生成
   * 鍵の生成には文字列ではなくバイト列が必要なので、String -> Byte[] 変換をしている
   */
  @PostConstruct // DI完了後に一度だけ実行する
  public void init() {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * JWT(Json Web Token)生成
   * @param user
   * @return token
   */
  public String generateToken(User user) {
    return Jwts.builder()
      .setSubject(user.getId())
      .claim("email", user.getEmail())
      .claim("name", user.getName())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
      .signWith(key)
      .compact();
  }
}
