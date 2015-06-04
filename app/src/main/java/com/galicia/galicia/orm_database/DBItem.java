package com.galicia.galicia.orm_database;

import com.orm.SugarRecord;

/**
 * Created by vasia on 26.05.2015.
 */
public class DBItem extends SugarRecord<DBItem> {

    private String pdf;
    private Shop shop;


    public DBItem() {
    }

    public DBItem(final String _pdf, Shop _shop){
        shop = _shop;
        pdf = _pdf;

    }

    public Shop getShop() {
        return shop;
    }

    public String getPdf() {
        return pdf;
    }

}
