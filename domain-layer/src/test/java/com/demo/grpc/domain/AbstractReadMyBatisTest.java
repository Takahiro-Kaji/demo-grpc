package com.demo.grpc.domain;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Read専用MyBatisテストのベースクラス
 * 固定seedデータを使用し、並列実行可能
 * 
 * 運用ルール:
 * - Readテストクラス/メソッドには @Tag("read") を付与
 * - @DataSet の cleanBefore は false に設定（固定seedデータを使用）
 * - 並列実行可能（CI側でJUnit並列実行がON）
 */
@SpringBootTest
@Testcontainers
public abstract class AbstractReadMyBatisTest {

    @Container
    @SuppressWarnings("resource") // Testcontainers automatically manages container lifecycle
    protected static final MySQLContainer<?> mysql = createMySQLContainer("read-db");

    /**
     * Read専用MySQLコンテナを作成する
     * 固定seedデータを使用するため、初期化スクリプトでデータを投入
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

