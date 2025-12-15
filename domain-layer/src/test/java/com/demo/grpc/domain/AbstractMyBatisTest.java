package com.demo.grpc.domain;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * MyBatisテストのベースクラス
 * MySQLコンテナとSpring設定を共通化
 */
@SpringBootTest
@Testcontainers
public abstract class AbstractMyBatisTest {

    @Container
    @SuppressWarnings("resource") // Testcontainers automatically manages container lifecycle
    protected static final MySQLContainer<?> mysql = createMySQLContainer();

    /**
     * MySQLコンテナを作成する
     * サブクラスでstatic初期化ブロックを使って初期化スクリプトなどを追加可能
     */
    @SuppressWarnings("resource") // Testcontainers automatically manages container lifecycle
    protected static MySQLContainer<?> createMySQLContainer() {
        return new MySQLContainer<>(TestContainersConfig.MYSQL_IMAGE)
                .withDatabaseName(TestContainersConfig.MYSQL_DATABASE)
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

