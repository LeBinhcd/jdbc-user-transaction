CREATE DATABASE IF NOT EXISTS user_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE user_management;
CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(100) NOT NULL,email VARCHAR(150) NOT NULL,country VARCHAR(100) NOT NULL);
CREATE TABLE IF NOT EXISTS permission (id INT PRIMARY KEY,name VARCHAR(50));
CREATE TABLE IF NOT EXISTS user_permission (user_id INT,permission_id INT,PRIMARY KEY(user_id,permission_id),FOREIGN KEY(user_id) REFERENCES users(id),FOREIGN KEY(permission_id) REFERENCES permission(id));
INSERT IGNORE INTO permission(id,name) VALUES (1,'add'),(2,'edit'),(3,'delete'),(4,'view');
CREATE TABLE IF NOT EXISTS Employee (id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(120) NOT NULL,salary DECIMAL(15,2) NOT NULL,created_Date TIMESTAMP);
INSERT INTO users(name,email,country) VALUES ('John','john@codegym.vn','Vietnam'),('Bill','bill@codegym.vn','USA'),('Alex','alex@codegym.vn','Vietnam');

