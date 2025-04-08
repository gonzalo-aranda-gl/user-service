CREATE TABLE user (
    id VARCHAR(36) NOT NULL UNIQUE,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    is_active VARCHAR(5),
    last_login TIMESTAMP,
    created TIMESTAMP,
    updated TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE phone (
    id VARCHAR(36) NOT NULL UNIQUE,
    number int,
    city_code int,
    country_code VARCHAR(36),
    user_id VARCHAR(36),
    created TIMESTAMP,
    updated TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);