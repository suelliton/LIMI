package com.example.suelliton.limi.models;

public class ItemColabora {
    private String chaveUsuario;
    private String nomeDieta;

    public ItemColabora() {
    }

    public ItemColabora(String chaveUsuario) {
        this.chaveUsuario = chaveUsuario;
    }

    public ItemColabora(String chaveUsuario, String nomeDieta) {
        this.chaveUsuario = chaveUsuario;
        this.nomeDieta = nomeDieta;
    }

    public String getChaveUsuario() {
        return chaveUsuario;
    }

    public void setChaveUsuario(String chaveUsuario) {
        this.chaveUsuario = chaveUsuario;
    }

    public String getNomeDieta() {
        return nomeDieta;
    }

    public void setNomeDieta(String nomeDieta) {
        this.nomeDieta = nomeDieta;
    }
}
