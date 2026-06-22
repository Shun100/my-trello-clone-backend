# SecurityFilterChainについて

- `DevSecurityConfig`にしても401エラーが返って来る
- 考えられる原因
  1. `DevSecurityConfig`が実際には読み込まれていない
  2. 別の`SecurityFilterChain`が存在する
  3. `JwtAuthenticationFilter`が`SecurityFilterChain`外で実行されている
     - これが原因最有力候補？
  4. `@EnableMethodSecurity` + `@PreAuthorize`などのメソッド認可