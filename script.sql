CREATE DATABASE RestauranteEstoque;
USE RestauranteEstoque;

CREATE TABLE Estoque (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ingrediente VARCHAR(100) NOT NULL,
    quantidade BIGINT NOT NULL,
    necessarioRepor BOOLEAN DEFAULT FALSE
);

CREATE TABLE Receita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ingredienteUsado VARCHAR(100) NOT NULL,
    quantidadeNecessaria BIGINT NOT NULL
);