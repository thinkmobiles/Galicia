package com.galicia.galicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.galicia.galicia.custom.circleprogress.CircleProgress;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Network;
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
    private CircleProgress mProgressView;
    private TextView mInfo;
    private boolean mIsLoadContent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        makeDownloadListener();
        mProgressView = (CircleProgress) findViewById(R.id.progress);
        mInfo = (TextView) findViewById(R.id.tvDownloadProcess);
        mProgressView.setVisibility(View.GONE);
        if (isHasContent()) {
            updateContent();
//            openNewActivity();
        } else {
            downloadContent();
        }
    }

    private void openMainActivityDelay() {
        ScheduledExecutorService worker =
                Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            public void run() {
                openNewActivity();
            }
        };
        worker.schedule(task, 1, TimeUnit.SECONDS);
    }



    private void updateContent() {
        if (Network.isInternetConnectionAvailable(this)){
            ApiManager.init(this);
            ApiManager.getLastUpdateServer(downloadListener);
        } else {
            openMainActivityDelay();
        }
    }

    private boolean isHasContent() {
        File f = new File(ApiManager.getPath(this));
        return f.exists();
    }

    private void openNewActivity() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        mProgressView.stopAnim();
        finish();
    }

    private void downloadContent() {
        if (Network.isInternetConnectionAvailable(this)) {
            mIsLoadContent = true;
            ApiManager.init(this);
            ApiManager.downloadContent(downloadListener);
            mProgressView.setVisibility(View.VISIBLE);
            mProgressView.startAnim();
        } else {
            finish();
        }
    }

    private void makeDownloadListener() {
        downloadListener = new EventListener() {

            @Override
            public void onEvent(final Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getBaseContext(), event.getType() + getString(R.string.error), Toast.LENGTH_LONG).show();
                        openNewActivity();
                        break;

                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                        SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
                        ApiManager.setOfflineMode();
                        openNewActivity();
                        break;

                    case AppModel.ChangeEvent.DOWNLOAD_FILE_CHANGED_ID:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mInfo.setText(event.getMessage());
                            }
                        });
                        break;

                    case AppModel.ChangeEvent.LAST_UPDATE_CHANGED_ID:
                        Log.d("Update", "Update");
                        runOnUiThread (new Thread(new Runnable() {
                            public void run() {
                                if (hasNewContent())
                                    downloadContent();
                                else
                                    openMainActivityDelay();
                            }
                        }));
                        break;
                }
            }
        };

    }

    @Override
    public void onBackPressed() {
        if (!mIsLoadContent) {
            super.onBackPressed();
        }
    }

    private boolean hasNewContent() {
        final Date currentUpdate = new Date(SharedPreferencesManager.getUpdateDate(getBaseContext()));
        final Date lastUpdate = getDate(ApiManager.getDateUpdate());
        if(currentUpdate.before(lastUpdate))
            return true;
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
