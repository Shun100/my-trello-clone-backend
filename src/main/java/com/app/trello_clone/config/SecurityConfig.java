package com.app.trello_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  public SecurityConfig() {
    System.out.println(">>> SecurityConfig Loaded");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            // /helloは誰でもアクセス可能
            .requestMatchers("/hello").permitAll()
            // その以外は認証必須
            .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable()); // REST API・テスト用に無効化

    return http.build();
  }
}
