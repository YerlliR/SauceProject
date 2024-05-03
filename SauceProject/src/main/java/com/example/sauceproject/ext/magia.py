import requests

def obtener_precio_crypto(criptomoneda):
    url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
    parametros = {
        'CMC_PRO_API_KEY': 'e3db4799-68cd-45e1-b4dd-af265faf8552'
    }

    try:
        respuesta = requests.get(url, params=parametros)
        datos = respuesta.json()
        precio_bitcoin = datos['data'][criptomoneda]['quote']['USD']['price']
        return precio_bitcoin
    except Exception as e:
        print("Error al obtener el precio de la criptomoneda:", e)
        return None

