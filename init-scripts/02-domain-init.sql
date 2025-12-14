SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE DATABASE IF NOT EXISTS demo_grpc_domain CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE demo_grpc_domain;

CREATE TABLE IF NOT EXISTS reservations (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    reserved_at DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    note VARCHAR(255) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_reservations_user_id (user_id)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT IGNORE INTO reservations (user_id, reserved_at, status, note) VALUES
('user-001', '2025-12-15 10:00:00', 'CONFIRMED', '会議室A'),
('user-001', '2025-12-16 14:30:00', 'CONFIRMED', '会議室B'),
('user-002', '2025-12-15 11:00:00', 'CONFIRMED', '会議室C'),
('user-003', '2025-12-17 09:00:00', 'CONFIRMED', '会議室A'),
('user-003', '2025-12-18 15:00:00', 'PENDING', '会議室B');

