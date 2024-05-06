create database SaucerWallet;
USE SaucerWallet;

CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Apellidos VARCHAR(255) NOT NULL,
    CorreoElectronico VARCHAR(255) NOT NULL,
    Usuario VARCHAR(50) NOT NULL,
    Contraseña VARCHAR(50) NOT NULL,
    TipoUsuario BOOLEAN default false
);



CREATE TABLE currencies (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    symbol VARCHAR(10),
    cmc_rank INT,
    last_updated VARCHAR(30),
    price DECIMAL(20, 2),
    percent_change_24h DECIMAL(10, 2),
    market_cap DECIMAL(30, 2)
);

select * from currencies order by market_cap DESC;