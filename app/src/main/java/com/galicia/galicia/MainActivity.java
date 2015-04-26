package com.galicia.galicia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cristaliza.mvc.controllers.estrella.MainController;
import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;



public class MainActivity extends ActionBarActivity {
    private Context context = null;
    /**
     * The listener reference for sending events
     */
    private MainViewListener controller; //Controller instance
    private AppModel model; //Model instance. Model is a sigleton

    private Button btnDownload, btnReadContent;
    private EditText txtWebservice;

    private ImageView image = null;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image = null;
        public DownloadImageTask(ImageView image){
            super();

            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            //return loadImageFromNetwork(urls[0]);
            return loadImageFromDevice(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            //Do something with bitmap eg:
            this.image.setImageBitmap(result);
        }
    }

    private Bitmap loadImageFromNetwork(String url){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap loadImageFromDevice(String url){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new FileInputStream(url));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        //Model
        model = AppModel.getInstance();

        //Controller
        controller = new MainController(); //Lets create a new controller instance. One instance per Window
        controller.setAsynchronousMode(); //Set working mode
        controller.setAppPLV(); //Set app we are working on

        //The Controller register its self as "Observator" in teh View to recive notifications...
        setViewListener(controller);


        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Downloading contents...", Toast.LENGTH_LONG);

                //Path in device to save data
                String path = Environment.getExternalStorageDirectory() + "/" + getApplicationContext().getPackageName();
                File f = new File(path);
                if(!f.exists()){
                    boolean b = f.mkdirs();
                    System.out.println(b);
                }

                //We do invoke a controller method!. We�ll be notified of the end of the work by the "DOWNLOAD_ALL_CHANGED" event.
                controller.downloadAllAppData(modelListener, path);
            }
        });

        btnReadContent = (Button) findViewById(R.id.btnReadContent);
        btnReadContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }
        });

        txtWebservice = (EditText) findViewById(R.id.txtWebService);

        //Now we, as the View, subscribe to Model events. The events we want to be notified.
        model.addListener(AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED, modelListener);
        model.addListener(AppModel.ChangeEvent.ON_EXECUTE_ERROR, modelListener); //We always want to be notified of any error.

        image = (ImageView) findViewById(R.id.imageView1);

    }


    /**
     * Updates the View with Model data
     */
    private final void bind() {
        //Get product info
        String res = "";
        if(model.getProducts().get(0) != null){
            new DownloadImageTask(image).execute(model.getOfflinePath() + "/" + model.getProducts().get(0).getBackgroundImage());

            res = model.getProducts().get(0).getName();
            res = model.getProducts().get(0).getCategory();
        }
        try{
            txtWebservice.setText(res);
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Allows the Controller to be notified by View actions.
     * @param viewListener
     */
    public void setViewListener(MainViewListener viewListener) {
        this.controller = viewListener;
    }


    /**
     * Model notifies changes to the View. So we invoke any bind() method to updates data in the screen!.
     */
    private EventListener modelListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            switch(event.getId()){
                case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                    //Any error?. event.getType() gives me what event name failed
                    System.out.println(event.getType());
                    break;
                case AppModel.ChangeEvent.APP_CONFIG_CHANGED_ID:
                    //En este caso, s�lo s� que ya tengo en el modelo de mi app la info. del XML de configuraci�n
                    System.out.println(model.getAppConfig().getParameter("base-url"));

                    controller.onExecuteWSFirstLevel(); // deja info en model.getRecomendedRadioStations()
                    break;
                case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
                    //Notified when controller.onExecuteWSFirstLevel() finishes;
                    System.out.println(model.getFirstLevel());

                    //lets load second level data in the model.
                    controller.onExecuteWSSecondLevel(model.getFirstLevel().get(0));
                    break;
                case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
                    //Notified when controller.onExecuteWSSecondLevel() finishes;
                    System.out.println(model.getSecondLevel());

                    //lets load third level data in the model.
                    controller.onExecuteWSThirdLevel(model.getSecondLevel().get(0));
                    break;
                case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                    //Notified when controller.onExecuteWSThirdLevel() finishes;
                    System.out.println(model.getThirdLevel());

                    //Lets load produts of last level in the model
                    controller.onExecuteWSProducts(model.getThirdLevel().get(0));
                    break;
                case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                    //Notified when controller.onExecuteWSProducts() finishes;
                    System.out.println(model.getProducts());

                    runOnUiThread (new Thread(new Runnable() {
                        public void run() {
                            //We do repaint the screen! on separate thread to avoid errors.
                            bind();
                        }
                    }));

                    break;
                case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                    System.out.println("Content downloaded!");

                    runOnUiThread (new Thread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Content downloaded!", Toast.LENGTH_LONG);
                        }
                    }));

                    break;
            }

        }

    };

    public void doStart() {
        //Sets device path to load info from
        String path = Environment.getExternalStorageDirectory() + "/" + getApplicationContext().getPackageName();
        AppModel.getInstance().setOfflinePath(path);

        model.removeListeners();

        //I know that download has finished!
        model.addListener(AppModel.ChangeEvent.APP_CONFIG_CHANGED, modelListener);
        model.addListener(AppModel.ChangeEvent.FIRST_LEVEL_CHANGED, modelListener);
        model.addListener(AppModel.ChangeEvent.SECOND_LEVEL_CHANGED, modelListener);
        model.addListener(AppModel.ChangeEvent.THIRD_LEVEL_CHANGED, modelListener);
        model.addListener(AppModel.ChangeEvent.PRODUCTS_CHANGED, modelListener);
        model.getFirstLevel().get(0).getBackgroundImage();
        model.getSecondLevelXML();
        //Lets load first level data in the Model class.
        controller.onExecuteWSFirstLevel();
    }




}
