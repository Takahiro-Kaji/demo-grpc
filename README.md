# gRPC デモプロジェクト

Spring Boot + gRPC のデモプロジェクトです。記事の内容に基づいて実装されています。

## プロジェクト構成

このプロジェクトは以下の2つのレイヤーで構成されています：

- **データ層（data-layer）**: gRPCサーバ + MyBatis + H2 Database
- **ドメイン層（domain-layer）**: REST API + gRPCクライアント

### 通信フロー

```
REST API (domain-layer:8080) 
  → gRPC Client 
    → gRPC Server (data-layer:9090) 
      → MyBatis 
        → H2 Database
```

## 前提条件

- Java 17以上
- Gradle 8.5以上（Gradle Wrapperが含まれています）

## セットアップ

### 1. プロジェクトのビルド

```bash
cd ~/demo-grpc
./gradlew build
```

### 2. protoファイルからコード生成

```bash
./gradlew :data-layer:generateProto
./gradlew :domain-layer:generateProto
```

## 実行方法

### 1. データ層（gRPCサーバ）の起動

```bash
cd ~/demo-grpc
./gradlew :data-layer:bootRun
```

データ層はポート **9090** で起動します。

### 2. ドメイン層（REST API + gRPCクライアント）の起動

別のターミナルで：

```bash
cd ~/demo-grpc
./gradlew :domain-layer:bootRun
```

ドメイン層はポート **8080** で起動します。

## API エンドポイント

### ユーザー取得

```bash
curl http://localhost:8080/api/users/user-001
```

### ユーザー一覧取得

```bash
curl http://localhost:8080/api/users?page=1&pageSize=10
```

## プロジェクト構造

```
demo-grpc/
├── proto/                    # protoファイル（共有）
│   └── user.proto
├── data-layer/               # データ層（gRPCサーバ）
│   ├── src/main/
│   │   ├── java/
│   │   │   └── com/demo/grpc/data/
│   │   │       ├── DataLayerApplication.java
│   │   │       ├── entity/
│   │   │       ├── mapper/
│   │   │       └── service/
│   │   ├── proto/            # protoファイル（コピー）
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── schema.sql
│   │       └── mapper/
│   └── build.gradle
├── domain-layer/             # ドメイン層（REST API + gRPCクライアント）
│   ├── src/main/
│   │   ├── java/
│   │   │   └── com/demo/grpc/domain/
│   │   │       ├── DomainLayerApplication.java
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       └── service/
│   │   ├── proto/            # protoファイル（コピー）
│   │   └── resources/
│   │       └── application.yml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 主要な技術スタック

- **Spring Boot 3.2.0**
- **gRPC** (grpc-spring-boot-starter)
- **MyBatis 3.0.3**
- **H2 Database** (インメモリDB、デモ用)
- **Lombok**
- **Gradle 8.5**

## 用語説明

- **proto**: gRPCの契約ファイル。メッセージ構造やサービス（メソッド）を定義
- **message**: リクエスト／レスポンスのデータ構造
- **service**: gRPCが提供するAPIのまとまり
- **rpc**: service内の1メソッド。リクエストとレスポンスの組を持つ
- **stub**: protoから自動生成されるクラス。サーバ側のベースクラスと、クライアント側の呼び出し窓口
- **unary RPC**: リクエスト1件 → レスポンス1件の基本形

## トラブルシューティング

### ポートが既に使用されている場合

- データ層: `application.yml` の `server.port` を変更
- ドメイン層: `application.yml` の `server.port` と `grpc.client.data-layer.address` を変更

### protoファイルを変更した場合

```bash
./gradlew clean build
```

を実行して、再ビルドしてください。

## 参考資料

- [Spring Boot - gRPC の話についていくためのインプット](https://qiita.com/taka_wow/private/351eb0996257b825ce76)

