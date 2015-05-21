package com.galicia.galicia.untils.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.Shop;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

/**
 * Created by Feltsan on 20.05.2015.
 */
public class ItemDAO extends GaliciaDBDAO {
    public ItemDAO(Context context) {
        super(context);
    }

    public long save(Item item, int shop_id){
        ContentValues values = new ContentValues();
        values.put(Constants.NAME_COLUMN, item.getName());
        values.put(Constants.COLUMN_LINK_IMAGE, item.getIcon());
        values.put(Constants.COLUMN_LINK_PDF, item.getPdf());
        values.put(Constants.COLUMN_ID_SHOP, shop_id);
        return database.insert(Constants.TABLE_ITEM, null, values);
    }

    public ArrayList<Item> getItems(){
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT " + Constants.ITEM_ID_WITH_PREFIX + ","
                + Constants.ITEM_NAME_WITH_PREFIX + ","
                + Constants.COLUMN_LINK_IMAGE + ","
                + Constants.COLUMN_LINK_PDF + ","
                + Constants.COLUMN_ID_SHOP + ","
                + Constants.SHOP_NAME_WITH_PREFIX
                + " FROM "
                + Constants.TABLE_ITEM + " i, "
                + Constants.TABLE_SHOP + " s WHERE i."
                + Constants.COLUMN_ID_SHOP + " =s."
                + Constants.ID_COLUMN;

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
        return items;
    }
}
