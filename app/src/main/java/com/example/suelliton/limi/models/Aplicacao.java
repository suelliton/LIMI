package com.example.suelliton.limi.models;

public class Aplicacao {
    private int dia;
    private int mes;
    private int ano;
    private float sobra;
    private float consumido;
    private float adicionado;
    private float consumoDiario;

    public Aplicacao() {
    }

    public Aplicacao(int dia, int mes, int ano, float sobra, float consumido, float adicionado, float consumoDiario) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.sobra = sobra;
        this.consumido = consumido;
        this.adicionado = adicionado;
        this.consumoDiario = consumoDiario;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public float getSobra() {
        return sobra;
    }

    public void setSobra(float sobra) {
        this.sobra = sobra;
    }

    public float getConsumido() {
        return consumido;
    }

    public void setConsumido(float consumido) {
        this.consumido = consumido;
    }

    public float getAdicionado() {
        return adicionado;
    }

    public void setAdicionado(float adicionado) {
        this.adicionado = adicionado;
    }

    public float getConsumoDiario() {
        return consumoDiario;
    }

    public void setConsumoDiario(float consumoDiario) {
        this.consumoDiario = consumoDiario;
    }
}
