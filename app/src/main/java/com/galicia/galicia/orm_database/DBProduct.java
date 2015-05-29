package com.galicia.galicia.orm_database;

import com.orm.SugarRecord;

/**
 * Created by vasia on 28.05.2015.
 */
public class DBProduct extends SugarRecord<DBProduct> {

    private String name;
    private String image;
    private DBItem item;

    public DBProduct(){

    }

    public DBProduct(String _name, String _image, DBItem _dbItem) {
        name = _name;
        image = _image;
        item = _dbItem;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public DBItem getItem() {
        return item;
    }
}
