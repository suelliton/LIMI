package com.example.suelliton.limi.models;

public class Dia {
    private String racao;
    private float adicionado;
    private float sobra;
    private float consumido;
    private float percentualConsumido;
    private int dia;
    private int mes;
    private int ano;

    public Dia() {
    }

    public Dia(String racao, float adicionado, float sobra, float consumido, float percentualConsumido, int dia, int mes, int ano) {
        this.racao = racao;
        this.adicionado = adicionado;
        this.sobra = sobra;
        this.consumido = consumido;
        this.percentualConsumido = percentualConsumido;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
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

    public String getRacao() {
        return racao;
    }

    public void setRacao(String racao) {
        this.racao = racao;
    }

    public float getAdicionado() {
        return adicionado;
    }

    public void setAdicionado(float adicionado) {
        this.adicionado = adicionado;
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

    public float getPercentualConsumido() {
        return percentualConsumido;
    }

    public void setPercentualConsumido(float percentualConsumido) {
        this.percentualConsumido = percentualConsumido;
    }

}
