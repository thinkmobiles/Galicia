package com.galicia.galicia.global;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.cristaliza.mvc.commands.estrella.AppConfigImpl;
import com.cristaliza.mvc.commands.estrella.LastUpdateImpl;
import com.cristaliza.mvc.models.estrella.AppConfig;
import com.cristaliza.mvc.models.estrella.AppModel;

import java.util.List;

/**
 * Created by Виталий on 04/08/2014.
 */
public abstract class Network {
    /**
     * check existing internet connection
     * @param _context
     * @return
     */
    public static final boolean isInternetConnectionAvailable(final Context _context) {
        final ConnectivityManager connectivityManager   = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo             = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static class LoaderConfig extends AsyncTask<AppModel, Void, AppConfig> {

        @Override
        protected AppConfig doInBackground(AppModel... params){
            AppModel model = params[0];

            AppConfig res = null;
            try {
                res = (AppConfig) new AppConfigImpl(model.getAppConfigPath()).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(AppConfig appConfig) {
            super.onPostExecute(appConfig);
            cancel(true);
        }
    }

    public static class LoaderDateOfUpdate extends AsyncTask<String, Void, List>{

        @Override
        protected List doInBackground(String... params) {
            List lItems = null;
            try {
                lItems = (List) new LastUpdateImpl(params[0]).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lItems;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            cancel(true);
        }
    }
}
