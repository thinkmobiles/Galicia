package com.galicia.galicia.global;

import android.content.Context;
import android.content.SharedPreferences;

import com.galicia.galicia.SplashScreen;


/**
 * Created by Sasha on 17.10.2014.
 */
public abstract class SharedPreferencesManager {

    public static void saveUpdateDate(final Context _context, final long time) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                SplashScreen.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(Constants.UPDATE_TIME, time).commit();
    }

    public static long getUpdateDate(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                 SplashScreen.class.getSimpleName(), Context.MODE_PRIVATE);
        return prefs.getLong(Constants.UPDATE_TIME, 0);
    }
}
