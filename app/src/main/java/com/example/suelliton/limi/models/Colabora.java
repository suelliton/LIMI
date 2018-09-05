package com.example.suelliton.limi.models;

import android.content.ClipData;

import java.util.ArrayList;
import java.util.List;

public class Colabora {
    private List<ItemColabora> usuarios;


    public Colabora() {
    }

    public Colabora(List<ItemColabora> usuarios) {
        this.usuarios = usuarios;
    }

    public List<ItemColabora> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<ItemColabora> usuarios) {
        this.usuarios = usuarios;
    }
}
