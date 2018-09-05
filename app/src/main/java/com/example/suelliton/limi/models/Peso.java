package com.example.suelliton.limi.models;

public class Peso {
    private int dia;
    private int mes;
    private int ano;
    private float gramas;

    public Peso(int dia, int mes, int ano, float gramas) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.gramas = gramas;
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

    public float getGramas() {
        return gramas;
    }

    public void setGramas(float gramas) {
        this.gramas = gramas;
    }
}
