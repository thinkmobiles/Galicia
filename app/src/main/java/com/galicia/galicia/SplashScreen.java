package com.galicia.galicia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.app.AlertDialog.OnClickListener;

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
        ApiManager.init(this);
        mProgressView = (CircleProgress) findViewById(R.id.progress);
        mInfo = (TextView) findViewById(R.id.tvDownloadProcess);
        mProgressView.setVisibility(View.GONE);
        checkContent();
    }


    private void checkContent(){
        if (Network.isInternetConnectionAvailable(this)) {
            if(isHasContent()){
                if (hasNewContent()) {
                    showDialogUpdate();
                } else {
                    openMainActivity();
                }
            } else {
                downloadContent();
            }
        } else {
            if (isHasContent()) {
                openMainActivity();
            } else {
                showDialogClose();
            }
        }
    }

    private void showDialogClose() {
        new AlertDialog.Builder(this)
                .setMessage("Compruebe la conexin a Internet por favor")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void showDialogUpdate() {
        new AlertDialog.Builder(this)
                .setMessage("Quiere descargar nuevo contenido ahora?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadContent();
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity();
                    }
                })
                .create()
                .show();
    }

    private void openMainActivityDelay() {
        ScheduledExecutorService worker =
                Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            public void run() {
                openMainActivity();
            }
        };
        worker.schedule(task, 1, TimeUnit.SECONDS);
    }

    private boolean isHasContent() {
        File f = new File(ApiManager.getPath(this));
        return f.exists();
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        mProgressView.stopAnim();
        finish();
    }

    private void downloadContent() {
            mIsLoadContent = true;
            ApiManager.downloadContent(downloadListener);
            mProgressView.setVisibility(View.VISIBLE);
            mProgressView.startAnim();
    }

    private void makeDownloadListener() {
        downloadListener = new EventListener() {

            @Override
            public void onEvent(final Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                        runOnUiThread(new Runnable() {
                            public void run() {
                            saveDownload();
                            }
                        });
                        break;
                    case AppModel.ChangeEvent.DOWNLOAD_FILE_CHANGED_ID:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mInfo.setText(event.getMessage());
                            }
                        });
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

    private void saveDownload() {
        Toast.makeText(getBaseContext(),"Descargando contenidos", Toast.LENGTH_LONG).show();
        SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
        openMainActivity();
    }

    private boolean hasNewContent() {
        Calendar currentUpdate = Calendar.getInstance();
        Calendar lastUpdate = Calendar.getInstance();
        currentUpdate.setTimeInMillis(SharedPreferencesManager.getUpdateDate(getBaseContext()));
        lastUpdate.setTime(getDate(ApiManager.getDate()));
        if(currentUpdate.before(lastUpdate))
            return true;
        return false;

    }

    private Date getDate(final String _date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
