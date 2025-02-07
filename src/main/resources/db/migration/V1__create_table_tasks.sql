CREATE TABLE tasks(
    id SERIAL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    finished BOOLEAN NOT NULL,
    priority TEXT NOT NULL,
    PRIMARY KEY (id)
);