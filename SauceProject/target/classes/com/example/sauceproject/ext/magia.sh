#!/bin/bash

# Definir la URL de la API y la clave de la API
URL="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
API_KEY="e3db4799-68cd-45e1-b4dd-af265faf8552"

# Hacer la solicitud a la API
respuesta=$(curl -s "$URL?CMC_PRO_API_KEY=$API_KEY")

# Extraer el precio de Bitcoin de la respuesta JSON
precio_bitcoin=$(echo "$respuesta" | jq -r '.data[0].quote.USD.price')

# Imprimir el precio de Bitcoin
echo "El precio actual de Bitcoin es: $precio_bitcoin"
