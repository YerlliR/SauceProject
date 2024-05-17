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

select * from Usuarios;

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
    id INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT,
    idCrypto INT,
    cantidadCryptomoneda DECIMAL (20,15) NOT NULL,
    precioPorCriptomoneda DECIMAL (20,15) NOT NULL,
    precioTotal DECIMAL(20, 15) NOT NULL,
    fechaDeTransaccionUsuario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fechaDeTransaccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(id),
    FOREIGN KEY (idCrypto) REFERENCES currencies(id)
);


select * FROM transacciones;

CREATE TABLE webViewrGraph (
    id INT PRIMARY KEY,
    symbol VARCHAR(10),
    cmc_rank INT,
    link VARCHAR(255)
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



UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=BINANCE%3ABTCUSD'
WHERE symbol = 'BTC';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AETHUSD'
WHERE symbol = 'ETH';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AUSDTUSD'
WHERE symbol = 'USDT';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ABNBUSD'
WHERE symbol = 'BNB';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ASOLUSD'
WHERE symbol = 'SOL';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AUSDCUSD'
WHERE symbol = 'USDC';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AXRPUSD'
WHERE symbol = 'XRP';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ADOGEUSD'
WHERE symbol = 'DOGE';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ATONUSD'
WHERE symbol = 'TON';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AADAUSD'
WHERE symbol = 'ADA';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ASHIBUSD'
WHERE symbol = 'SHIB';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AAVAXUSD'
WHERE symbol = 'AVAX';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ATRXUSD'
WHERE symbol = 'TRX';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ADOTUSD'
WHERE symbol = 'DOT';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ABCHUSD'
WHERE symbol = 'BCH';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ALINKUSD'
WHERE symbol = 'LINK';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ANEARUSD'
WHERE symbol = 'NEAR';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AMATICUSD'
WHERE symbol = 'MATIC';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ALTCUSD'
WHERE symbol = 'LTC';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AICPUSD'
WHERE symbol = 'ICP';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ALEUSD'
WHERE symbol = 'LEO';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ADAIUSD'
WHERE symbol = 'DAI';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AUNIUSD'
WHERE symbol = 'UNI';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AETCUSD'
WHERE symbol = 'ETC';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ARNDRUSD'
WHERE symbol = 'RNDR';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AFDUSD'
WHERE symbol = 'FDUSD';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AHBARUSD'
WHERE symbol = 'HBAR';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AAPTUSD'
WHERE symbol = 'APT';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3AATOMUSD'
WHERE symbol = 'ATOM';

UPDATE webViewrGraph
SET link = 'https://es.tradingview.com/chart/?symbol=CRYPTO%3ACROUSD'
WHERE symbol = 'CRO';









SELECT
    c.name AS Nombre_Criptomoneda,
    c.symbol AS Símbolo,
    c.price AS Precio_Actual,
    c.percent_change_24h AS Cambio_Porcentual_24h,
    SUM(t.cantidadCryptomoneda) AS Cantidad_Total,
    ((c.price - t.precioPorCriptomoneda) / t.precioPorCriptomoneda) * 100 AS Rentabilidad,
    (t.cantidadCryptomoneda * (c.price - t.precioPorCriptomoneda)) AS Perdidas_Ganancias
FROM
    Usuarios u
JOIN
    transacciones t ON u.id = t.idUsuario
JOIN
    currencies c ON t.idCrypto = c.id
WHERE
    u.id = '1'
GROUP BY
    c.id;




SELECT
    c.name AS Nombre_Criptomoneda,
    c.symbol AS Símbolo,
    c.price AS Precio_Actual,
    c.percent_change_24h AS Cambio_Porcentual_24h,
    SUM(t.cantidadCryptomoneda) AS Cantidad_Total,
    ((c.price - AVG(t.precioPorCriptomoneda)) / AVG(t.precioPorCriptomoneda)) * 100 AS Rentabilidad,
    SUM(t.cantidadCryptomoneda * (c.price - t.precioPorCriptomoneda)) AS Perdidas_Ganancias
FROM
    Usuarios u
JOIN
    transacciones t ON u.id = t.idUsuario
JOIN
    currencies c ON t.idCrypto = c.id
WHERE
    u.id = '1'
GROUP BY
    c.id, c.name, c.symbol, c.price, c.percent_change_24h;
