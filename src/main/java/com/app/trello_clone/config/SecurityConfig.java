package com.app.trello_clone.config;

import com.app.trello_clone.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration // DIコンテナに読み込ませる設定
@Profile("!dev")
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Value("${cors.allowed-origin}")
  private String allowedOrigin;

  @Bean // DIコンテナに登録
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigSource())) // CORS設定
        .csrf(AbstractHttpConfigurer::disable) // CSRF無効化 (REST API用)
        .sessionManagement(session ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ) // JWT認証のみにするため、セッション認証無効化
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers(
              "/hello",
              "/auth/signup",
              "/auth/signin").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore( // JWT検証
          jwtAuthenticationFilter,
          UsernamePasswordAuthenticationFilter.class
        ); // Beforeなので、authorizeHttpRequestsよりも先にこちらが実行される

    return http.build();
  }

  /**
   * CORS設定
   * @return source - CORS設定情報
   */
  @Bean
  public CorsConfigurationSource corsConfigSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(allowedOrigin));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true); // JWT Authorization Header許可用

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
