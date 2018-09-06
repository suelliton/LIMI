package com.example.suelliton.limi.models;

import java.util.ArrayList;
import java.util.List;

public class Tratamento {
    private List<Peso> pesos;
    private int numero ;
    private String nome;

    public Tratamento() {
    }

    public Tratamento(int numero, String nome) {
        this.pesos = new ArrayList<>();
        this.numero = numero;
        this.nome = nome;
    }

    public List<Peso> getPesos() {
        return pesos;
    }

    public void setPesos(List<Peso> pesos) {
        this.pesos = pesos;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
