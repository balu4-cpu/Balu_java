-- =============================================================
-- Exam Seating Arrangement System — Database Setup Script
-- Run this script as MySQL root user.
-- =============================================================

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS exam_seating_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. Create the application user and set password
CREATE USER IF NOT EXISTS 'exam_app'@'localhost' IDENTIFIED BY 'change-this-password';
ALTER USER 'exam_app'@'localhost' IDENTIFIED BY 'change-this-password';

-- 3. Grant privileges
GRANT ALL PRIVILEGES ON exam_seating_db.* TO 'exam_app'@'localhost';
FLUSH PRIVILEGES;
