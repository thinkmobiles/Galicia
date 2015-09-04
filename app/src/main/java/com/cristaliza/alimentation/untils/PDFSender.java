package com.cristaliza.alimentation.untils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.global.ApiManager;
import com.cristaliza.alimentation.global.Constants;
import com.cristaliza.alimentation.orm_database.DBItem;
import com.cristaliza.alimentation.orm_database.DBManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feltsan on 22.05.2015.
 */
public abstract class PDFSender {

    public static void sendShopPDFs (Activity activity, long pos){
        // ItemDAO i = new ItemDAO(activity);
        List<DBItem> items = DBManager.getItems(pos);
        ArrayList<Uri> uris =new ArrayList<>();
        //  List<Item> items = i.getItems(String.valueOf(pos));

        for (int k=0; k<items.size(); k++){
            File file = new File(ApiManager.getPath() + items.get(k).getPdf());
            File newFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + file.getName());
            try {
                copy(file, newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!file.exists() || !file.canRead()) {
                Toast.makeText(activity, activity.getString(R.string.attachment_error), Toast.LENGTH_SHORT).show();
                return;
            }

            uris.add(Uri.fromFile(newFile));

        }

        Intent mailer = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mailer.setType(Constants.TYPE_MESSAGE);
        mailer.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        activity.startActivityForResult(Intent.createChooser(mailer, activity.getString(R.string.send_mail)), 10);
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

}
