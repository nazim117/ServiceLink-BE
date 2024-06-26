CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    postalCode VARCHAR(10) NOT NULL,
    country VARCHAR(60) NOT NULL
);
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    datetime DATETIME NOT NULL,
    description VARCHAR(255) NOT NULL
);
CREATE TABLE provisions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address_id INT,
    description TEXT,
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name ENUM('CLIENT', 'SERVICE_PROVIDER', 'ADMIN') NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
