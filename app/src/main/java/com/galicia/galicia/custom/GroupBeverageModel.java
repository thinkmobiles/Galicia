package com.galicia.galicia.custom;

import com.cristaliza.mvc.models.estrella.Item;

import java.util.List;

public final class GroupBeverageModel {
    public String title;
    public List<Item> beverageModels;

    public GroupBeverageModel(String title, List<Item> beverageModels) {
        this.title = title;
        this.beverageModels = beverageModels;
    }
}
