package com.cristaliza.alimentacion.untils.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.alimentacion.global.Constants;

import java.util.ArrayList;

/**
 * Created by Feltsan on 20.05.2015.
 */
public class ItemDAO extends GaliciaDBDAO {
    public ItemDAO(Context context) {
        super(context);
    }

    public void save(Item item, int shop_id){
        ContentValues values = new ContentValues();
        values.put(Constants.NAME_COLUMN, item.getName());
        values.put(Constants.COLUMN_LINK_IMAGE, item.getIcon());
        values.put(Constants.COLUMN_LINK_PDF, item.getPdf());
        values.put(Constants.COLUMN_ID_SHOP, shop_id);
        database.insert(Constants.TABLE_ITEM, null, values);

    }

    public ArrayList<Item> getItems(String shopId){
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT "
                + Constants.ID_COLUMN + ","
                + Constants.NAME_COLUMN + ","
                + Constants.COLUMN_LINK_IMAGE + ","
                + Constants.COLUMN_LINK_PDF + ","
                + Constants.COLUMN_ID_SHOP
                + " FROM "
                + Constants.TABLE_ITEM
                + " WHERE "
                + Constants.COLUMN_ID_SHOP + " = "
                + shopId;

        Log.d("query", query);
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()){
            Item item = new Item();
            item.setId(String.valueOf(cursor.getInt(0)));
            item.setName(cursor.getString(1));
            item.setIcon(cursor.getString(2));
            item.setPdf(cursor.getString(3));
            items.add(item);
        }
        Log.d("ZZZ", String.valueOf(cursor.getCount()));
        return items;
    }
    public  long updateItem(Item item){
        ContentValues values = new ContentValues();
        values.put(Constants.NAME_COLUMN, item.getName());
        values.put(Constants.COLUMN_LINK_IMAGE, item.getIcon());
        values.put(Constants.COLUMN_LINK_PDF, item.getPdf());

        long result = database.update(Constants.TABLE_ITEM,values,
                Constants.WHERE_ID_EQUALS,
                new String[]{item.getId()});
        Log.d("Update result:","=" + result);
        return result;
    }
    public int deleteItem(Item item){
        return database.delete(Constants.TABLE_ITEM,
                Constants.WHERE_ID_EQUALS,
                new String[]{item.getId() + ""});
    }
    public void deleteAll(String shop_id){
        database.delete(Constants.TABLE_ITEM,Constants.WHERE_SHOP_ID_EQUALS,new String[]{shop_id + ""});
    }
}
