package com.example.sauceproject.ext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class jsonDowload {

    public static void ejecut() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=e3db4799-68cd-45e1-b4dd-af265faf8552";
        String ruta = "src/main/resources/com/example/sauceproject/json/data.json";

        try {
            descargaArchivo(url, ruta);
            System.out.println("Archivo descargado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void descargaArchivo(String url, String ruta) throws IOException {
        URL connection = new URL(url);
        InputStream inputStream = connection.openStream();

        try (FileOutputStream outputStream = new FileOutputStream(ruta)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        inputStream.close();
    }
}

