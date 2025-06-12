-- Creazione della tabella
DROP TABLE IF EXISTS countries;
CREATE TABLE countries (
    alpha2code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL,
    capital VARCHAR(100) NOT NULL,
    flag VARCHAR(255)
);