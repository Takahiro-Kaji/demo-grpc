SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE DATABASE IF NOT EXISTS demo_grpc CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE demo_grpc;

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT IGNORE INTO users (user_id, name, email) VALUES
('user-001', '山田太郎', 'yamada@example.com'),
('user-002', '佐藤花子', 'sato@example.com'),
('user-003', '鈴木一郎', 'suzuki@example.com');

