package com.cristaliza.alimentacion.untils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.alimentacion.global.ApiManager;
import com.cristaliza.alimentacion.untils.DataBase.ItemDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feltsan on 22.05.2015.
 */
public abstract class PDFSender {

   public static void sendShopPDFs (Activity activity, int pos){
        ItemDAO i = new ItemDAO(activity);
        ArrayList<Uri> uris =new ArrayList<>();
        List<Item> items = i.getItems(String.valueOf(pos));

        for (int k=0; k<items.size(); k++){
            File file = new File(ApiManager.getPath() + items.get(k).getPdf());
            if (!file.exists() || !file.canRead()) {
                Toast.makeText(activity, "Attachment Error", Toast.LENGTH_SHORT).show();
                return;
            }
            uris.add(Uri.fromFile(file));

        }

        Intent mailer = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mailer.setType("message/rfc822");
        mailer.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        activity.startActivity(Intent.createChooser(mailer, "Send email..."));
    }

   public static void sendItemPDF(Context context,String string){

        File file = new File(ApiManager.getPath() + string);
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(context, "Attachment Error", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse("file://" + file);
        Intent mailer = new Intent(Intent.ACTION_SEND);
        mailer.setType("message/rfc822");
        mailer.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(mailer, "Send email..."));
   }

}
