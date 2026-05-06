# DIについての備忘録

## 1. DIコンテナに登録する情報の定義方法 (Bean定義の定義方法)

- Bean定義 = DIコンテナに登録する情報 のこと
- BeanとはSpringの独自用語であり、Javaの標準用語であるJavaBeanとは無関係
- 定義方法は3種類ある
  - 1. ステレオタイプアノテーション (`@Service`等)
    - 付与対象: クラス
    - このアノテーションを付与したクラスはDIコンテナによって自動的にコンストラクタが呼ばれ、インスタンスがDIコンテナに格納される
    - **一番使用頻度が高い**

    | ステレオアノテーションタイプ | 付与対象                       | 付加機能                                                 |
    | ---------------------------- | ------------------------------ | -------------------------------------------------------- |
    | `@Service`                   | Service層のクラス              | なし                                                     |
    | `@Repository`                | Repository層のクラス           | DBアクセスまわりの例外を、Springが提供する例外に変換可能 |
    | `@Controller`                | Controller層のクラス           | Spring MVCの機能を利用可能                               |
    | `@Component`                 | 上記のいずれにも属さないクラス | なし                                                     |

  - 2. `@Bean`アノテーション
    - 付与対象: メソッド
    - DIコンテナに格納したい情報を「戻り値」として返すようにしておく DIコンテナは`@Bean`アノテーションを自動検出してメソッドを呼び出し、戻り値をDIコンテナに格納してくれる

  - 3. `<bean>`タグ (xmlファイル)
    - Beanとして管理してほしいクラスをclass属性で指定し、XMLファイルをDIコンテナに読み込ませる
      DIコンテナは指定されたクラスのコンストラクタを呼び出し、インスタンスをDIコンテナに格納する
    ```xml
      <bean class="com.example.foo.service.FooService"></bean>
    ```

## 2. DIコンテナに情報を登録する方法 (コンポーネントスキャン)

### 2-1. コンポーネントスキャンの設定クラスを定義する

- ステレオタイプアノテーションをクラスに付与すれば自動的にDIコンテナに格納されると説明したが、正確には違う
- コンポーネントスキャンというアノテーションをスキャンして検知する処理が必要
  - コンポーネントスキャンはコンフィグレーションで有効にする必要がある

  ```Java

    @Configuration
    @ComponentScan // コンフィグレーションクラスに付与する
    public class TrainingApplicationConfig {
      ...
    }
  ```

  - すると、`@ComponentScan`を付与したクラスが所属するパッケージを起点にして、サブパッケージを含めてステレオタイプアノテーションが付与されたクラスをDIコンテナが探してくれる

### 2-2. コンポーネントスキャンを実行する

- 実行方法は2種類ある
  - 1. `AnnotationConfigApplicationContext`クラスを使用した方法
    - `AnnotationConfigApplicationContext`クラスはDIコンテナの元となるクラス
    - コンストラクタにコンフィグレーションクラスを渡すことで、コンフィグレーションの内容を読み取ってコンポーネントスキャンを実行する

  ```Java
    public static void main(String[] args) {
      ApplicationContext context = new AnnotationConfigApplicationContext(TrainingApplicationConfig.class);
    }

  ```

  - 2. `SpringApplication`クラスの`run`メソッドを使用した方法
    - **WIP: 勉強中**

## 3. DIコンテナに登録した情報を依存注入する方法 (インジェクションの方法)

- ステレオタイプアノテーションはDIコンテナに格納するまでの話であって、その先の依存注入は別途必要
- 注入対象のメソッドに`@Autowired`アノテーションを付与することで、DIコンテナが対応するインスタンスを探し出して注入してくれる
- 注入方法は3種類ある
  - 1. コンストラクタインジェクション
    - コンストラクタの引数として依存オブジェクトを注入する
    - 3種類の中で最も一般的
    - この方法だけのメリットとして、フィールドにfinal修飾子を付けられる

    ```Java
      // Service層に対してRepository層のインスタンスを注入する

      @Service
      public Class TrainingServiceImpl implements TrainingService {
        private TrainingRepository trainingRepository;

        @Autowired // コンストラクタにアノテーションを付与する
        public TrainingServiceImpl(TrainingRepository trainingRepository) {
          this.trainingRepository = trainingRepository;
        }

        public List<Training> findAll() {
          return trainingRepository.selectAll();
        }
      }
    ```

  - 2. Setterインジェクション
    - Setterメソッドの引数で依存オブジェクトを注入する
    - Setterメソッドに`@Autowired`アノテーションを付与することで、DIコンテナがSetterメソッドを呼び出して引数の型に合致するBeanを渡してくれる

    ```Java
      @Service
      public class TrainingServiceImpl implements TrainingService {
        private TrainingRepository trainingRepository;

        @Autowired // Setterメソッドにアノテーションを付与する
        public void setTrainingRepository(TrainingRepository trainingRepository) {
          this.trainingRepository = trainingRepository;
        }
      }
    ```

  - 3. フィールドインジェクション
    - フィールドに直接依存オブジェクトを注入する

    ```Java
      @Service public class TrainingServiceImpl implements TrainingService {

        @Autowired // フィールドにアノテーションを付与する
        private TrainingRepository trainingRepository;
      }
    ```

## 4. まとめ

- 1. `@Controller`, `@Service`, `@Repository`アノテーションを付けてBean定義する
- 2. `@Configuration`アノテーションを付けてコンポーネントスキャンの設定クラスを作る
- 3. `Main`クラスの中で、`AnnotationConfigApplicationContext`クラスをインスタンス化してコンポーネントスキャンを実行する
- 4. 注入対象のメソッドに`@Autowired`アノテーションを付けて、依存注入する
