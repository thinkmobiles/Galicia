package com.cristaliza.alimentacion.models;

import java.io.Serializable;

/**
 * Created by Feltsan on 20.05.2015.
 */
public class Shop implements Serializable{
    private int id;
    private String name;

    public Shop() {
    }

    public Shop(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Shop(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
