package com.galicia.galicia.untils.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feltsan on 20.05.2015.
 */
public class ShopDAO extends GaliciaDBDAO {


    public ShopDAO(Context context) {
        super(context);
    }

    public long save(Shop shop){
        ContentValues values = new ContentValues();
        values.put(Constants.NAME_COLUMN, shop.getName());

        return database.insert(Constants.TABLE_SHOP, null, values);
    }

    public List<Shop> getShops(){
        List<Shop> shops = new ArrayList<Shop>();
        Cursor cursor = database.query(Constants.TABLE_SHOP, new String[]{Constants.ID_COLUMN, Constants.NAME_COLUMN }, null, null,null,null,null );

        while (cursor.moveToNext()){
            Shop shop = new Shop();
            shop.setId(cursor.getInt(0));
            shop.setName(cursor.getString(1));
            shops.add(shop);
        }
        return shops;
    }

    public void loadShops() {
        Shop shop = new Shop("Good Food");
        Shop shop1 = new Shop("Zina");
        Shop shop2 = new Shop("Silpo");


        List<Shop> departments = new ArrayList<Shop>();
        departments.add(shop);
        departments.add(shop1);
        departments.add(shop2);

        for (Shop sho : departments) {
            ContentValues values = new ContentValues();
            values.put(Constants.NAME_COLUMN, sho.getName());
            database.insert(Constants.TABLE_SHOP, null, values);
        }
    }

}
