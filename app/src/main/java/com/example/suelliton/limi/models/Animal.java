package com.example.suelliton.limi.models;

public class Animal {

    private String nome;
    private String sexo;
    private float peso;

    public Animal() {
    }

    public Animal(String nome, String sexo, float peso) {
        this.nome = nome;
        this.sexo = sexo;
        this.peso = peso;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
