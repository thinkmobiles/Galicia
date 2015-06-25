package com.cristaliza.alimentation.global;

import android.content.Context;
import com.cristaliza.alimentation.untils.TypefaceUtil;
import com.orm.SugarApp;

public class App extends SugarApp {
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), Constants.NAME_FONT_FAMILY, Constants.PATH_FONT); // font from assets: "assets/fonts/century_gothic.ttf

        context = this;
    }
}
