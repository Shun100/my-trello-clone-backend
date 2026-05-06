# my-trello-clone-backend

## 1. プロジェクトの作成手順

- [Spring Initializr](https://start.spring.io/)

- 設定項目
  - Project: `Maven`
  - Language: `Java`
  - Spring Boot: `4.0.6`
  - Project Metadata
    - Group: `com.app`
    - Artifact: `trello-clone`
    - Package name: `com.app.trello-clone`
    - Packaging: `Jar`
    - Configuration: `Properties`
    - Java: `21`
  - Dependencies
    - `Spring Data JPA`: ORM
    - `Spring Security`: 認証機能用
    - `Spring Web`: Spring MVCでRESTful APIを実装する場合は必須
    - `PostgreSQL Driver`: PostgreSQL用のJDBC
    - `Validation`: 引数チェック用
    - `Spring Boot DevTools`: ホットリロード機能

## 2. 動作確認手順

- `src/main/resources/application.properties`にDB用の設定を追加
- `src/main/java/com/app/trello_clone/config`にセキュリティ設定用のクラスを作成
  - `SecurityConfig.java`

- 動作確認用のクラスを作成

  ```Java
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class HelloController {

      @GetMapping("/hello")
      public String hello() {
        return "Hello, I am Spring Boot";
      }
    }
  ```

- 実行
  - `./mvnw spring-boot:run`
