# my-trello-clone-backend

## 1. プロジェクトの作成

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
