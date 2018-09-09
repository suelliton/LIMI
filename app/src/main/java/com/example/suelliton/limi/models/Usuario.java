package com.example.suelliton.limi.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String username;
    private String email;
    private String password;
    private boolean colaborador;
    private String experimentos;


    public Usuario(){

    }
    public Usuario(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.experimentos = "";
    }


    public boolean isColaborador() {
        return colaborador;
    }

    public void setColaborador(boolean colaborador) {
        this.colaborador = colaborador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
