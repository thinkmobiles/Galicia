package com.galicia.galicia.models;

import com.cristaliza.mvc.models.estrella.Item;

import java.io.Serializable;

public class ItemSerializable implements Serializable {
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
