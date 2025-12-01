CREATE TABLE users (
    id serial not null PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    roles VARCHAR(20) NOT NULL
);

INSERT INTO users (username, password, enabled, roles) VALUES
    ('admin', '$2a$12$zV3.uxFDiLY2/miia00r.OF82I2Lclj/S7L8G36ZlsXPVka1RVieq', TRUE, 'ADMIN'),
    ('user', '$2a$12$zV3.uxFDiLY2/miia00r.OF82I2Lclj/S7L8G36ZlsXPVka1RVieq', TRUE, 'USER');