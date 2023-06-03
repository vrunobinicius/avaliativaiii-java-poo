CREATE DATABASE avaliacao
USE avaliacao

CREATE TABLE cliente (
    ID bigint PRIMARY KEY AUTO_INCREMENT,
    cpf bigint NOT NULL,
    nome varchar(45) NOT NULL
);

CREATE TABLE contrato (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    redacao TEXT,
    ultima_atualizacao DATE NOT NULL,
    cliente_ID bigint,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

DESC cliente
DESC contrato