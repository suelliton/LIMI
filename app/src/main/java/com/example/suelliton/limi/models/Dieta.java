package com.example.suelliton.limi.models;

import java.util.ArrayList;
import java.util.List;

public class Dieta {
    private String nome;
    private String descricao;
    private List<Racao> racoes;

    public Dieta() {
    }

    public Dieta(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.racoes = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Racao> getRacoes() {
        return racoes;
    }

    public void setRacoes(List<Racao> racoes) {
        this.racoes = racoes;
    }
}
