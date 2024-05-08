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
    price DECIMAL(20, 10),
    percent_change_24h DECIMAL(10, 2),
    market_cap DECIMAL(30, 2)
);

select * from currencies order by market_cap DESC;


CREATE TABLE transacciones (
    id INT PRIMARY KEY,
    idUsuario INT,
    idCrypto INT,
    precioCompra DECIMAL(20, 15) NOT NULL,
    fechaDeCompra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fechaDeTransaccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(id),
    FOREIGN KEY (idCrypto) REFERENCES currencies(id)
);


CREATE TABLE webViewrGraph (
    id INT PRIMARY KEY,
    symbol VARCHAR(10),
    cmc_rank INT
);

INSERT INTO webViewrGraph (id, symbol, cmc_rank)
SELECT id, symbol, cmc_rank
FROM currencies
ORDER BY cmc_rank
LIMIT 30;



select * from webViewrGraph order by cmc_rank;



SELECT cg.symbol, c.percent_change_24h
FROM webViewrGraph cg
JOIN currencies c ON cg.id = c.id AND cg.symbol = c.symbol
ORDER BY cg.cmc_rank
LIMIT 30;
