package com.cristaliza.nacional.untils.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.cristaliza.nacional.global.Constants;
import com.cristaliza.nacional.models.Shop;

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

    public int deleteShop(Shop shop) {
        database.delete(Constants.TABLE_ITEM,Constants.WHERE_SHOP_ID_EQUALS,  new String[]{shop.getId() + ""});
        return database.delete(Constants.TABLE_SHOP,
                Constants.WHERE_ID_EQUALS,
                new String[]{shop.getId() + ""});
    }

    public void deleteAll(){
        database.delete(Constants.TABLE_ITEM,null,null);
        database.delete(Constants.TABLE_SHOP,null,null);
    }

}
