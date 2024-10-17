package com.conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyAPI {
    private static final String API_KEY = "b05d35eaad7aa303370829dd";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static JsonObject getExchangeRates(String baseCurrency) throws Exception {
        String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return JsonParser.parseString(response.body()).getAsJsonObject();
    }
}
