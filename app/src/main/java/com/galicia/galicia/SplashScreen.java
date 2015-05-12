package com.galicia.galicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.models.estrella.AppModel;

/**
 * Created by Bogdan on 06.05.2015.
 */
public class SplashScreen extends Activity {

    private MainViewListener controller; //Controller instance
    private AppModel model; //Model instance. Model is a sigleton
    private ImageView image = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

//        ScheduledExecutorService worker =
//                Executors.newSingleThreadScheduledExecutor();
//        Runnable task = new Runnable() {
//            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
//            }
//        };
//        worker.schedule(task, 2, TimeUnit.SECONDS);

//        DownloadContent();
    }


//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView image = null;
//        public DownloadImageTask(ImageView image){
//            super();
//
//            this.image = image;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            //return loadImageFromNetwork(urls[0]);
//            return loadImageFromDevice(urls[0]);
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            //Do something with bitmap eg:
//            this.image.setImageBitmap(result);
//        }
//    }
//
//    private Bitmap loadImageFromNetwork(String url){
//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return bitmap;
//    }
//
//    private Bitmap loadImageFromDevice(String url){
//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream((InputStream)new FileInputStream(url));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return bitmap;
//    }
//
//    private void DownloadContent(){
//
//        model = AppModel.getInstance();
//
//        //Controller
//        controller = new MainController(); //Lets create a new controller instance. One instance per Window
//        controller.setAsynchronousMode(); //Set working mode
//        controller.setAppPLV();
//        controller = controller;
//
//        String path = Environment.getExternalStorageDirectory() + "/" + getApplicationContext().getPackageName();
//        File f = new File(path);
//        if(!f.exists()){
//            boolean b = f.mkdirs();
//            System.out.println(b);
//        }
//        model.addListener(AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED, modelListener);
//        model.addListener(AppModel.ChangeEvent.ON_EXECUTE_ERROR, modelListener);
//        //We do invoke a controller method!. We�ll be notified of the end of the work by the "DOWNLOAD_ALL_CHANGED" event.
//        controller.downloadAllAppData(modelListener, path);
//    }
//
//    private final void bind() {
//        //Get product info
//        String res = "";
//        if(model.getProducts().get(0) != null){
//            new DownloadImageTask(image).execute(model.getOfflinePath() + "/" + model.getProducts().get(0).getBackgroundImage());
//
//            res = model.getProducts().get(0).getName();
//            res = model.getProducts().get(0).getCategory();
//        }
//        try{
////            txtWebservice.setText(res);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    private EventListener modelListener = new EventListener() {
//        @Override
//        public void onEvent(Event event) {
//            switch(event.getId()){
//                case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
//                    //Any error?. event.getType() gives me what event name failed
//                    System.out.println(event.getType());
//                    break;
//                case AppModel.ChangeEvent.APP_CONFIG_CHANGED_ID:
//                    //En este caso, s�lo s� que ya tengo en el modelo de mi app la info. del XML de configuraci�n
//                    System.out.println(model.getAppConfig().getParameter("base-url"));
//
//                    controller.onExecuteWSFirstLevel(); // deja info en model.getRecomendedRadioStations()
//                    break;
//                case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
//                    //Notified when controller.onExecuteWSFirstLevel() finishes;
//                    System.out.println(model.getFirstLevel());
//
//                    //lets load second level data in the model.
//                    controller.onExecuteWSSecondLevel(model.getFirstLevel().get(0));
//                    break;
//                case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
//                    //Notified when controller.onExecuteWSSecondLevel() finishes;
//                    System.out.println(model.getSecondLevel());
//
//                    //lets load third level data in the model.
//                    controller.onExecuteWSThirdLevel(model.getSecondLevel().get(0));
//                    break;
//                case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
//                    //Notified when controller.onExecuteWSThirdLevel() finishes;
//                    System.out.println(model.getThirdLevel());
//
//                    //Lets load produts of last level in the model
//                    controller.onExecuteWSProducts(model.getThirdLevel().get(0));
//                    break;
//                case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
//                    //Notified when controller.onExecuteWSProducts() finishes;
//                    System.out.println(model.getProducts());
//
//                    runOnUiThread (new Thread(new Runnable() {
//                        public void run() {
//                            //We do repaint the screen! on separate thread to avoid errors.
//                            bind();
//                        }
//                    }));
//
//                    break;
//                case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
//                    System.out.println("Content downloaded!");
//
//                    runOnUiThread (new Thread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(getBaseContext(), "Content downloaded!", Toast.LENGTH_LONG);
//                        }
//                    }));
//
//                    break;
//            }
//
//        }
//
//    };


}
