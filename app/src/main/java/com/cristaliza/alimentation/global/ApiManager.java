package com.cristaliza.alimentation.global;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;

import com.cristaliza.mvc.controllers.estrella.MainController;
import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppConfig;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class ApiManager {
    private static AppModel model;
    private static MainViewListener controller;
    private static String path = null;
    public static void setPath(Context context) {
//        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName();
        ContextWrapper cw = new ContextWrapper(context);
        path = cw.getDir(context.getPackageName(), Context.MODE_PRIVATE).getAbsolutePath() + "/" +context.getPackageName();
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
        ContextWrapper cw = new ContextWrapper(context);
        path = cw.getDir(context.getPackageName(), Context.MODE_PRIVATE).getAbsolutePath() + "/" +context.getPackageName();
        controller = new MainController(model);
        controller.setAppAlimentacion();
    }
    public static void downloadContent(EventListener listener){
        File f = new File(path);
        if(!f.exists()){
            boolean b = f.mkdirs();
            System.out.println(b);
        }
        controller.setAsynchronousMode();
        controller.downloadAllAppData(listener, path);
    }
    public static void getLastUpdateServer(EventListener listener) {
        controller.onExecuteWSAppConfig();
        model.setOnlineMode(true);
        model.addListener(AppModel.ChangeEvent.LAST_UPDATE_CHANGED, listener);
        controller.setAsynchronousMode();
        model.setOfflinePath(path);
        controller.onExecuteWSAppLastUpdate();
    }
    public static String getDateUpdate() {
        return model.getLastUpdate();
    }
    public static void setOfflineMode(){
        controller.setSynchronousMode();
        model.setOfflinePath(path);
        model.setOnlineMode(false);
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
    public static List<Item> getFirstList() {
        List<Item> list = model.getFirstLevel();
        return list;
    }
    public static List<Item> getSecondList() {
        return model.getSecondLevel();
    }
    public static List<Item> getThirdList() {
        return model.getThirdLevel();
    }
    public static List<Product> getProductsList() {
        return model.getProducts();
    }
    public static void getProducts(EventListener listener, Item item) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.PRODUCTS_CHANGED, listener);
        controller.onExecuteWSProducts(item);
    }
    private static void setAppConfig(){
        String url = path + "/" + model.getApp().getId() + "/" + "levels" + "/app-config.xml";
        model.setOnlineMode(true);
        Network.LoaderConfig loader = new Network.LoaderConfig();
        loader.execute(model);
        AppConfig res = null;
        try {
            res = loader.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        model.setAppConfig(res);
    }
    public static String getDate(){
        setAppConfig();
        model.setOnlineMode(false);
        String url = model.getAppConfig().getParameter("base-url")
                + model.getAppConfig().getParameter("ws-plv-last-update");
        Network.LoaderDateOfUpdate loader = new Network.LoaderDateOfUpdate();
        loader.execute(url);
        List lItems = null;
        try {
            lItems = loader.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        AppModel.getInstance().setLastUpdate(((Item) lItems.get(0)).getUpdate());
        return ((Item)lItems.get(0)).getUpdate();
    }
}
