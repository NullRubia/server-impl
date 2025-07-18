create database posts;
use posts;

CREATE TABLE users (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    userid       VARCHAR(50) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    name         VARCHAR(50) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE posts (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    content     TEXT NOT NULL,
    writer_id   INT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (writer_id) REFERENCES users(id) ON DELETE CASCADE
);