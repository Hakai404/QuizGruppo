-- Creazione della tabella
DROP TABLE IF EXISTS countries;
CREATE TABLE countries (
    alpha2code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL,
    capital VARCHAR(100) NOT NULL,
    flag VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS games(
    id int auto_increment primary key,
    user_name varchar(255),
    difficulty varchar(50),
    score int,
    attempts int,
    modalita varchar(50)
);