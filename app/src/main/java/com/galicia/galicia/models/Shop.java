package com.galicia.galicia.models;

/**
 * Created by Feltsan on 20.05.2015.
 */
public class Shop {
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
