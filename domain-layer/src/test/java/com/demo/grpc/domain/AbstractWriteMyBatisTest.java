package com.demo.grpc.domain;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Write専用MyBatisテストのベースクラス
 * 各テストでcleanBefore=trueのデータセットを使用し、直列実行推奨
 * 
 * 運用ルール:
 * - Writeテストは用途に応じて以下を付与:
 *   - Create系：@Tag("create")
 *   - Update系：@Tag("update")
 *   - Delete系：@Tag("delete")
 * - Writeテストは cleanBefore=true のデータセット投入（DBRider想定）
 * - Writeテストは直列前提（CI側で並列OFF）
 */
@SpringBootTest
@Testcontainers
public abstract class AbstractWriteMyBatisTest {

    @Container
    @SuppressWarnings("resource") // Testcontainers automatically manages container lifecycle
    protected static final MySQLContainer<?> mysql = createMySQLContainer("write-db");

    /**
     * Write専用MySQLコンテナを作成する
     * サブクラスでstatic初期化ブロックを使って初期化スクリプトなどを追加可能
     */
    @SuppressWarnings("resource") // Testcontainers automatically manages container lifecycle
    protected static MySQLContainer<?> createMySQLContainer(String databaseSuffix) {
        return new MySQLContainer<>(TestContainersConfig.MYSQL_IMAGE)
                .withDatabaseName(TestContainersConfig.MYSQL_DATABASE + "_" + databaseSuffix)
                .withUsername(TestContainersConfig.MYSQL_USERNAME)
                .withPassword(TestContainersConfig.MYSQL_PASSWORD)
                .withCommand("--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci");
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("mybatis.type-aliases-package", () -> "com.demo.grpc.domain.dto");
        registry.add("mybatis.configuration.map-underscore-to-camel-case", () -> true);
        // gRPC clientはテストでは不要なので無効化
        registry.add("grpc.client.data-layer.address", () -> "static://localhost:9090");
    }
}

