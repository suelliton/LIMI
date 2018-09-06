package com.example.suelliton.limi.models;

import java.util.ArrayList;
import java.util.List;

public class Racao {
    private String nome;
    private List<Aplicacao> aplicacoes;
    private List<Tratamento> tratamentos;

    public Racao() {
    }

    public Racao(String nome) {
        this.nome = nome;
        this.aplicacoes = new ArrayList<>();
        this.tratamentos = new ArrayList<>();
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

    public List<Tratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(List<Tratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }
}
