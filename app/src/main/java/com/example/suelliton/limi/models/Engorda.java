package com.example.suelliton.limi.models;

import java.util.List;

public class Engorda {
    private List<Tratamento> tratamentos;
    private String animal;

    public Engorda(List<Tratamento> tratamentos, String animal) {
        this.tratamentos = tratamentos;
        this.animal = animal;
    }

    public List<Tratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(List<Tratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
}
