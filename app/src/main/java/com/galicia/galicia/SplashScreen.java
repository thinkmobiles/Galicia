package com.galicia.galicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.ProgressDialogWorker;
import com.galicia.galicia.global.SharedPreferencesManager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends Activity {

    private EventListener downloadListener;
//    private CircleProgress mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
//        mProgressView = (CircleProgress) findViewById(R.id.progress);
//        mProgressView.startAnim();
        if (isHasContent()) {
//            if(hasNewContent()){
//                makeDownloadListener();
//                updateContent();
//            } else {
                ScheduledExecutorService worker =
                        Executors.newSingleThreadScheduledExecutor();
                Runnable task = new Runnable() {
                    public void run() {
                        openNewActivity();
                    }
                };
                worker.schedule(task, 1, TimeUnit.SECONDS);
//            }
        } else {
//            ProgressDialogWorker.createDialog(this);
            makeDownloadListener();
            downloadContent();
        }
    }

    private boolean isHasContent() {
        File f = new File(ApiManager.getPath(this));
        return f.exists();
    }

    private void openNewActivity() {
//        mProgressView.stopAnim();
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }

    private void downloadContent() {
        ApiManager.init(this);
        ApiManager.downloadContent(downloadListener);
    }

    private void makeDownloadListener() {
        downloadListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getBaseContext(), event.getType() + " error", Toast.LENGTH_LONG).show();
                        break;
                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
//                        todo if download finish
//                        runOnUiThread (new Thread(new Runnable() {
//                            @Override
//                            public void run() {
                                SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
//                                ProgressDialogWorker.dismissDialog();
                                ApiManager.setOfflineMode();
                                openNewActivity();
//                            }
//                        }));

                        break;
                    case AppModel.ChangeEvent.LAST_UPDATE_CHANGED_ID:
//                        todo Last Update
                        runOnUiThread (new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
                                ProgressDialogWorker.dismissDialog();
                                ApiManager.setOfflineMode();
                                openNewActivity();
                            }
                        }));

                        break;
                }
            }
        };

    }

    private void updateContent() {
        ApiManager.init(this);
        ApiManager.getLastUpdateServer(downloadListener);
    }

    private boolean hasNewContent() {
        ApiManager.init(this);
        final Date currentUpdate = new Date(SharedPreferencesManager.getUpdateDate(getBaseContext()));
        final Date lastUpdate = getDate(ApiManager.getDateUpdate());
//        if(currentUpdate.before(lastUpdate))
//            return true;
        return false;

    }

    private Date getDate(final String _date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(2000,1,1);
        if(_date != null) {
            try {
                date = format.parse(_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
