-- Создаем базу данных
CREATE DATABASE NoteApiDb;

-- Переключаемся на созданную базу данных
\c NoteApiDb;

-- Создаем таблицы в базе данных
CREATE TABLE IF NOT EXISTS notes (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);
