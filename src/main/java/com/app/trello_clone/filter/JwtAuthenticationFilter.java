package com.app.trello_clone.filter;

import com.app.trello_clone.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  /**
   * JWT検証
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {

    System.out.println("=== JWT FILTER RUN ===");

    final String authHeader = request.getHeader("Authorization");

    // Bearer Tokenが無い場合は次のfilterに処理を委譲
    // ここで401を返してしまうと、新規登録やログインができなくなる
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // "Bearer xxx" -> "xxx"
    String token = authHeader.substring(7);

    // JWT検証 検証に失敗したら「401 Unauthorized」を返す
    if (!jwtService.validateToken(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
      return;
    }

    // userId取得
    String userId = jwtService.getUserId(token);

    // Springに渡すための認証情報を作る
    UsernamePasswordAuthenticationToken authentication =
      new UsernamePasswordAuthenticationToken(
        userId,
        null, // JWTなのでパスワードは不要
        Collections.emptyList() // TODO: ロール認可が使えるようにする
      );

    // 認証結果をセットする 監査ログに残す等、後続処理で使う場合に必要
    authentication.setDetails(
      new WebAuthenticationDetailsSource().buildDetails(request)
    );

    // Springに認証情報を渡し、このリクエストは認証済みであることを伝える
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 次のFilterに処理を移譲
    filterChain.doFilter(request, response);
  }
}