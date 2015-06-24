package com.cristaliza.alimentacion.global;

import android.app.Application;
import android.content.Context;

import com.cristaliza.alimentacion.untils.TypefaceUtil;

public class App extends Application {
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "HELVETICA", "assets/fonts/helvetica_roman.ttf"); // font from assets: "assets/fonts/century_gothic.ttf

        context = this;
    }
}
