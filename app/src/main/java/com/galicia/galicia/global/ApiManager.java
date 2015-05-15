package com.galicia.galicia.global;

import android.content.Context;
import android.os.Environment;

import com.cristaliza.mvc.controllers.estrella.MainController;
import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.io.File;
import java.util.List;

public abstract class ApiManager {

    private static AppModel model;
    private static MainViewListener controller;
    private static String path = null;

    public static void setPath(Context context) {
        path = Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
    }

    public static String getPath(Context context) {
        setPath(context);
        return path;
    }

    public static String getPath() {
        return path;
    }

    public static void init(Context context) {
        model = AppModel.getInstance();
        path = Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
        controller = new MainController();
        controller.setAsynchronousMode();
        controller.setAppGalicia();
    }

    public static void downloadContent(EventListener listener){
        File f = new File(path);
        if(!f.exists()){
            boolean b = f.mkdirs();
            System.out.println(b);
        }
        controller.downloadAllAppData(listener, path);
    }

    public static void setOfflineMode(){
        controller.setSynchronousMode();
        model.setOfflinePath(path);
    }

    public static void getFirstLevel(EventListener listener){
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.FIRST_LEVEL_CHANGED, listener);
        controller.onExecuteWSFirstLevel();
    }

    public static void getSecondLevel(EventListener listener, Item item){
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.SECOND_LEVEL_CHANGED, listener);
        controller.onExecuteWSSecondLevel(item);
    }

    public static void getThirdLevel(EventListener listener, Item item) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.THIRD_LEVEL_CHANGED, listener);
        controller.onExecuteWSThirdLevel(item);
    }

    public static void getLastUpdateServer(EventListener listener) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.LAST_UPDATE_CHANGED, listener);
        controller.onExecuteWSAppLastUpdate();
    }

    public static List<Item> getFirstList() {
        return model.getFirstLevel();
    }

    public static List<Item> getSecondList() {
        return model.getSecondLevel();
    }

    public static List<Item> getThirdList() {
        return model.getThirdLevel();
    }

    public static List<Product> getProducts() {
        return model.getProducts();
    }

}
