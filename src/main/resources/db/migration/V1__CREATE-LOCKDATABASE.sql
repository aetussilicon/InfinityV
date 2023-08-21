CREATE TABLE lockapi (
    id BINARY(16) PRIMARY KEY,
    website VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);
