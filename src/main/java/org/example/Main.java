package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitura = new Scanner(System.in);
        ConsultaMoeda consulta = new ConsultaMoeda();
        List<RegistroDeConversao> historico = new ArrayList<>();
        GeradorDeArquivo gerador = new GeradorDeArquivo();

        int opcao = 0;

        String menu = """
                ***********************************************
                Seja bem-vindo ao Conversor de Moedas!
                
                1) Dólar (USD) => Peso Argentino (ARS)
                2) Peso Argentino (ARS) => Dólar (USD)
                3) Dólar (USD) => Real Brasileiro (BRL)
                4) Real Brasileiro (BRL) => Dólar (USD)
                5) Dólar (USD) => Peso Colombiano (COP)
                6) Peso Colombiano (COP) => Dólar (USD)
                7) Sair
                
                Escolha uma opção válida:
                ***********************************************
                """;

        do {
            System.out.println(menu);

            if (leitura.hasNextInt()) {
                opcao = leitura.nextInt();
            } else {
                System.out.println("Por favor, digite um número.");
                leitura.next();
                continue;
            }

            if (opcao == 7) {
                try {
                    if (!historico.isEmpty()) {
                        gerador.salvaJson(historico);
                        System.out.println("Histórico salvo com sucesso em 'historico.json'!");
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
                }
                break; // Sai do loop imediatamente
            }

            // Se for uma opção válida (1 a 6)
            if (opcao >= 1 && opcao <= 6) {
                System.out.println("Digite o valor que deseja converter:");
                double valor = leitura.nextDouble();

                String moedaBase = "";
                String moedaAlvo = "";

                switch (opcao) {
                    case 1 -> { moedaBase = "USD"; moedaAlvo = "ARS"; }
                    case 2 -> { moedaBase = "ARS"; moedaAlvo = "USD"; }
                    case 3 -> { moedaBase = "USD"; moedaAlvo = "BRL"; }
                    case 4 -> { moedaBase = "BRL"; moedaAlvo = "USD"; }
                    case 5 -> { moedaBase = "USD"; moedaAlvo = "COP"; }
                    case 6 -> { moedaBase = "COP"; moedaAlvo = "USD"; }
                }

                try {
                    Moedas dados = consulta.buscaMoeda(moedaBase);
                    double taxa = dados.conversion_rates().get(moedaAlvo);
                    double valorConvertido = valor * taxa;

                    System.out.printf("Valor %.2f [%s] corresponde ao valor final de =>>> %.2f [%s]%n",
                            valor, moedaBase, valorConvertido, moedaAlvo);

                    String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    RegistroDeConversao novoRegistro = new RegistroDeConversao(moedaBase, moedaAlvo, valor, valorConvertido, agora);
                    historico.add(novoRegistro);

                } catch (Exception e) {
                    System.out.println("Erro na conversão: " + e.getMessage());
                }
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 7);

        System.out.println("Programa finalizado. Até logo!");
    }
}