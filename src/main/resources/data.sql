CREATE TABLE user (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(12) NOT NULL,
    created TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE phone (
    id VARCHAR(36) NOT NULL,
    number int,
    user_id VARCHAR(36),
    city_code VARCHAR(36),
    country_code VARCHAR(36),
    created TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);