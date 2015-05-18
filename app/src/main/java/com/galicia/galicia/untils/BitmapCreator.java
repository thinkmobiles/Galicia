package com.galicia.galicia.untils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.galicia.galicia.global.ApiManager;

/**
 * Created by Bogdan on 16.05.2015.
 */
public abstract class BitmapCreator {

    public static final Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }
}
