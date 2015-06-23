package com.cristaliza.nacional.untils.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cristaliza.nacional.global.Constants;

/**
 * Created by Feltsan on 19.05.2015.
 */
public class GaliciaDataBaseHelper extends SQLiteOpenHelper{
    private static GaliciaDataBaseHelper instance;


    public GaliciaDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static synchronized GaliciaDataBaseHelper getHelper(Context context){
        if(instance == null){
            instance = new GaliciaDataBaseHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        }
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL(Constants.OPEN_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_SHOP_TABLE);
        db.execSQL(Constants.CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DELETE_SHOP_TABLE);
        db.execSQL(Constants.DELETE_ITEM_TABLE);
        onCreate(db);
    }
}
