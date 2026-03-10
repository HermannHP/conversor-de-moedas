package org.example;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoeda {


    public Moedas buscaMoeda(String moedaBase) {
        String chave = "SUA-CHAVE-AQUI";
        URI endereco = URI.create("https://v6.exchangerate-api.com/v6/" + chave + "/latest/" + moedaBase);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(endereco).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new Gson().fromJson(response.body(), Moedas.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar moeda: " + e.getMessage());
        }
    }
}