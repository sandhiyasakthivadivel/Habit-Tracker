-- File: habitmate_schema.sql

CREATE DATABASE IF NOT EXISTS habitmate;

\c habitmate;

CREATE TABLE habits (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    frequency VARCHAR(20), -- e.g., daily, weekly
    status VARCHAR(20), -- e.g., pending, completed
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
