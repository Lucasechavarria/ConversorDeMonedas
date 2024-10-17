package com.conversor;

import com.google.gson.JsonObject;
import java.text.DecimalFormat;

public class CurrencyConverter {

    // Método para obtener la tasa de cambio de una moneda específica
    public static double getExchangeRate(JsonObject rates, String currencyCode) {
        return rates.getAsJsonObject("conversion_rates").get(currencyCode).getAsDouble();
    }

    // Método para convertir entre dos monedas y formatear el resultado
    public static double convertCurrency(double amount, double rateFrom, double rateTo) {
        return amount * (rateTo / rateFrom);
    }

    // Método para formatear el resultado según el número de decimales
    public static String formatResult(double value, int decimals) {
        // Utilizar String.repeat() para generar el patrón de formato.
        String pattern = "0." + "0".repeat(Math.max(0, decimals));
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }
}