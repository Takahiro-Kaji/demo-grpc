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

-- Read専用DBの固定seedデータ
INSERT IGNORE INTO reservations (reservation_id, user_id, reserved_at, status, note) VALUES
(1, 'user-001', '2025-12-15 10:00:00', 'CONFIRMED', '会議室A'),
(2, 'user-001', '2025-12-16 14:30:00', 'CONFIRMED', '会議室B'),
(3, 'user-002', '2025-12-15 11:00:00', 'CONFIRMED', '会議室C'),
(4, 'user-003', '2025-12-17 09:00:00', 'CONFIRMED', '会議室A'),
(5, 'user-003', '2025-12-18 15:00:00', 'PENDING', '会議室B');

