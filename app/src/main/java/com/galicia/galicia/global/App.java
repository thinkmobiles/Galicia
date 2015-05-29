package com.galicia.galicia.global;

import android.content.Context;
import com.galicia.galicia.untils.TypefaceUtil;
import com.orm.SugarApp;

public class App extends SugarApp {
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "HELVETICA", "assets/fonts/helvetica_roman.ttf"); // font from assets: "assets/fonts/century_gothic.ttf

        context = this;
    }
}
