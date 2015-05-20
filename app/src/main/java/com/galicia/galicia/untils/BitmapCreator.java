package com.galicia.galicia.untils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.galicia.galicia.global.ApiManager;

/**
 * Created by Bogdan on 16.05.2015.
 */
public abstract class BitmapCreator {

    public static final Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    public static String getAbsolutePath(String _path){
        return ApiManager.getPath() + _path;
    }

    public static final Drawable getDrawable(String _path) {
        return Drawable.createFromPath(ApiManager.getPath() + _path);
    }
}
