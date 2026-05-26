package com.app.trello_clone.service;

import com.app.trello_clone.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
   * 署名ありJWT
   * @param user
   * @return token
   *
   * {
   *   "sub": "...",
   *   "email": "...",
   *   "name": "...",
   *   "iat": ...,
   *   "exp": ...
   * }
   * というJSONデータ (JWTのpayload) を作ってJWTに入れたのち、JWT payload全体に対して署名
   */
  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId().toString())
        .claim("email", user.getEmail())
        .claim("name", user.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(key) // 署名
        .compact();
  }

  /**
   * JWT検証
   * 署名ありJWT
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        // .parsePlaintextJws(token); 署名なしJWT用
        .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * JWTからClaims取得
   * @param token
   * @return claims
   */
  public Claims getClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token) // ここで検証 署名の正しさ、期限、JWT形式、改ざんの有無
      .getBody();
  }

  /**
   * userId(sub)取得
   * @param token
   * @return userId
   */
  public String getUserId(String token) {
    return getClaims(token).getSubject();
  }
}
