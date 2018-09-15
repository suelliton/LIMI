package com.example.suelliton.limi.models;

import java.util.List;

public class Experimento {
    private String nome;
    private List<Animal> animais;
    private String descricao;
    private String especie;
    private int qtd_animais;

    public Experimento() {
    }

    public Experimento(String nome, List<Animal> animais, String descricao, String especie, int qtd_animais) {
        this.nome = nome;
        this.animais = animais;
        this.descricao = descricao;
        this.especie = especie;
        this.qtd_animais = qtd_animais;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    public int getQtd_animais() {
        return qtd_animais;
    }

    public void setQtd_animais(int qtd_animais) {
        this.qtd_animais = qtd_animais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
