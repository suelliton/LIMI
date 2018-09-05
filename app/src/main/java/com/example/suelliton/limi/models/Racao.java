package com.example.suelliton.limi.models;

import java.util.List;

public class Racao {
    private String nome;
    private List<Aplicacao> aplicacoes;
    private Engorda engorda;

    public Racao(String nome, List<Aplicacao> aplicacoes, Engorda engorda) {
        this.nome = nome;
        this.aplicacoes = aplicacoes;
        this.engorda = engorda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aplicacao> getAplicacoes() {
        return aplicacoes;
    }

    public void setAplicacoes(List<Aplicacao> aplicacoes) {
        this.aplicacoes = aplicacoes;
    }

    public Engorda getEngorda() {
        return engorda;
    }

    public void setEngorda(Engorda engorda) {
        this.engorda = engorda;
    }
}
