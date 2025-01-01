INSERT INTO users (username, password, role)
VALUES ('nikit', '$2b$12$ijpDhjosp9lLhkwbrFfEPeOqHF8BTtih.5whssdE39WZNQhLa9VIK', 'USER');



CREATE TABLE IF NOT EXISTS users
(
    id  SERIAL,
    username VARCHAR(64) NOT NULL UNIQUE ,
    password VARCHAR(128),
    role VARCHAR(32)
);
