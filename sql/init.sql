CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;

CREATE TABLE IF NOT EXISTS city (
   id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   UNIQUE KEY uk_name(name)
);


INSERT IGNORE INTO city (id, name) VALUES (1, "Barcelona");
INSERT IGNORE INTO city (id, name) VALUES (2, "Sabadell");
INSERT IGNORE INTO city (id, name) VALUES (3, "London");