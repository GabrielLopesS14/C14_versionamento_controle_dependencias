package org.example.quiz;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FutebolQuiz {

    public static void main(String[] args) {
        //Carregando as perguntas de um JSON
        List<Pergunta> perguntas = carregarPerguntas();

        Scanner scanner = new Scanner(System.in);
        int pontos = 0;

        System.out.println("Bem-vindo ao Genio Quiz");
        System.out.println("Tema de hoje: FUTEBOL");

        //Embaralhando as perguntas
        Collections.shuffle(perguntas);

        //Limitar o número de perguntas a 5, ou ao número de perguntas disponíveis(teste inicial)
        int numPerguntas = Math.min(perguntas.size(), 5);

        //Perguntas são feitas até o número limite
        for (int i = 0; i < numPerguntas; i++) {
            Pergunta pergunta = perguntas.get(i);
            System.out.println("\nPergunta " + (i + 1) + ": " + pergunta.getPergunta());
            List<String> respostas = pergunta.getRespostas();
            for (int j = 0; j < respostas.size(); j++) {
                System.out.println((j + 1) + ". " + respostas.get(j));
            }

            int respostaUsuario = -1;

            //Garantindo que o usuário forneça uma resposta válida
            while (respostaUsuario < 1 || respostaUsuario > respostas.size()) {
                System.out.print("Digite o número da sua resposta: ");
                if (scanner.hasNextInt()) {
                    respostaUsuario = scanner.nextInt();
                    if (respostaUsuario < 1 || respostaUsuario > respostas.size()) {
                        System.out.println("Resposta invalida. Tente novamente.");
                    }
                } else {
                    System.out.println("Por favor, insira um número valido.");
                    scanner.next(); //Limpando entrada recusada
                }
            }

            //Subtrai 1 para ajustar ao índice da lista (começa do 0) - Tava dando erro!!
            respostaUsuario--;

            if (respostaUsuario == pergunta.getRespostaCorreta()) {
                pontos++;
                System.out.println("Resposta correta!");
            } else {
                System.out.println("Resposta errada! A resposta correta era: " + respostas.get(pergunta.getRespostaCorreta()));
            }
        }

        System.out.println("\nFim do jogo! Você acertou " + pontos + " de " + numPerguntas + " perguntas.");
    }

    private static List<Pergunta> carregarPerguntas() {
        List<Pergunta> perguntas = new ArrayList<>();
        try {
            //Leitura do arquivo JSON
            FileReader reader = new FileReader("perguntas.json");
            JsonArray perguntasJson = JsonParser.parseReader(reader).getAsJsonArray();

            for (int i = 0; i < perguntasJson.size(); i++) {
                JsonObject perguntaJson = perguntasJson.get(i).getAsJsonObject();
                String perguntaTexto = perguntaJson.get("pergunta").getAsString();
                JsonArray respostasJson = perguntaJson.getAsJsonArray("respostas");

                List<String> respostas = new ArrayList<>();
                for (int j = 0; j < respostasJson.size(); j++) {
                    respostas.add(respostasJson.get(j).getAsString());
                }

                int respostaCorreta = perguntaJson.get("correta").getAsInt();

                perguntas.add(new Pergunta(perguntaTexto, respostas, respostaCorreta));
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar perguntas: " + e.getMessage());
        }
        return perguntas;
    }
}
