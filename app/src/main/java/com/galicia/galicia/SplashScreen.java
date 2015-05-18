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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends Activity {

    private EventListener downloadListenr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (isHasContent()) {
            ScheduledExecutorService worker =
                    Executors.newSingleThreadScheduledExecutor();
            Runnable task = new Runnable() {
                public void run() {
                    openNewActivity();
                }
            };
            worker.schedule(task, 1, TimeUnit.SECONDS);
        } else {
            ProgressDialogWorker.createDialog(this);
            makeDownloadListener();
            downloadContent();
        }
    }

    private boolean isHasContent() {
        File f = new File(ApiManager.getPath(this));
        return f.exists();
    }

    private void openNewActivity() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }

    private void downloadContent() {
        ApiManager.init(this);
        ApiManager.downloadContent(downloadListenr);
    }

    private void makeDownloadListener() {
        downloadListenr = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getBaseContext(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
//                        todo if download finish
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
                    case AppModel.ChangeEvent.LAST_UPDATE_CHANGED_ID:
//                        todo Last Update
                        SharedPreferencesManager.getUpdateDate(getBaseContext());

                        break;
                }
            }
        };

    }

    private void isHasNewContent() {
        ApiManager.init(this);
        ApiManager.getLastUpdateServer(downloadListenr);
    }

    private void asf() {
        final long currentTime = SharedPreferencesManager.getUpdateDate(getBaseContext());

    }


}
