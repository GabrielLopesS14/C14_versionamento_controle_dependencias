package org.example.quiz;

import java.util.List;

public class Pergunta {
    private String pergunta;
    private List<String> respostas;
    private int respostaCorreta;

    public Pergunta(String pergunta, List<String> respostas, int respostaCorreta) {
        this.pergunta = pergunta;
        this.respostas = respostas;
        this.respostaCorreta = respostaCorreta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public List<String> getRespostas() {
        return respostas;
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }
}
