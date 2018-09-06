package com.example.suelliton.limi.models;

import java.util.ArrayList;
import java.util.List;

public class Dieta {
    private String nome;
    private String descricao;
    private String usuario;
    private String animal;
    private List<Racao> racoes;

    public Dieta() {
    }


    public Dieta(String nome, String descricao, String usuario, String animal) {
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
        this.animal = animal;
        this.racoes = new ArrayList<>();
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
