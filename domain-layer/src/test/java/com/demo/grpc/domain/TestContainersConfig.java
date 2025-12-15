package com.demo.grpc.domain;

import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainersの設定を一元管理するクラス
 */
public final class TestContainersConfig {
    
    /**
     * MySQLコンテナのDockerイメージ名
     */
    public static final DockerImageName MYSQL_IMAGE = DockerImageName.parse("mysql:8.4.5");
    
    /**
     * MySQLコンテナのデータベース名
     */
    public static final String MYSQL_DATABASE = "demo_grpc_domain";
    
    /**
     * MySQLコンテナのユーザー名
     */
    public static final String MYSQL_USERNAME = "root";
    
    /**
     * MySQLコンテナのパスワード
     */
    public static final String MYSQL_PASSWORD = "rootpassword";
    
    private TestContainersConfig() {
        // インスタンス化を防ぐ
    }
}

