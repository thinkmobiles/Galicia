package com.cristaliza.alimentacion.orm_database;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by vasia on 26.05.2015.
 */
public class Shop extends SugarRecord<Shop> implements Serializable {

    private String mName;

    public Shop() {
    }

    public Shop(String _name) {
        mName = _name;
    }

    public String getName() {
        return mName;
    }
}
