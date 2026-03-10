package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeradorDeArquivo {
    public void salvaJson(List<RegistroDeConversao> lista) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escrita = new FileWriter("historico.json");
        escrita.write(gson.toJson(lista));
        escrita.close();
    }
}
