package com.cristaliza.alimentation.models;

import com.cristaliza.mvc.models.estrella.Item;

import java.io.Serializable;

public class ItemSerializable implements Serializable {
    private transient Item item;

    public ItemSerializable(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
