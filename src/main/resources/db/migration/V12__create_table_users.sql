CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO users (login, password, role) VALUES
('admin', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3ueN5nUC7Tf/05Jik/8.0.0.0.0.0', 'ADMIN');