package com.galicia.galicia.untils.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.galicia.galicia.untils.DataBase.GaliciaDataBaseHelper;


/**
 * Created by Feltsan on 20.05.2015.
 */
public class GaliciaDBDAO {
    protected SQLiteDatabase database;
    private GaliciaDataBaseHelper dbHelper;
    private Context context;

    public GaliciaDBDAO(Context context){
        this.context = context;
        dbHelper = GaliciaDataBaseHelper.getHelper(context);
        open();
    }

    public void open(){
        if(dbHelper ==null)
            dbHelper = GaliciaDataBaseHelper.getHelper(context);
        database = dbHelper.getWritableDatabase();

    }
}
