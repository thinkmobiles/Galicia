package com.galicia.galicia.untils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.orm_database.DBItem;
import com.galicia.galicia.orm_database.DBManager;
import com.galicia.galicia.untils.DataBase.ItemDAO;

import java.io.File;
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
            if (!file.exists() || !file.canRead()) {
                Toast.makeText(activity, activity.getString(R.string.attachment_error), Toast.LENGTH_SHORT).show();
                return;
            }
            uris.add(Uri.fromFile(file));

        }

        Intent mailer = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mailer.setType(Constants.TYPE_MESSAGE);
        mailer.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        activity.startActivity(Intent.createChooser(mailer, activity.getString(R.string.send_mail)));
    }

   public static void sendItemPDF(Context context,String string){

        File file = new File(ApiManager.getPath() + string);
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(context, context.getString(R.string.attachment_error), Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse(Constants.PATH_FILE + file);
        Intent mailer = new Intent(Intent.ACTION_SEND);
        mailer.setType(Constants.TYPE_MESSAGE);
        mailer.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(mailer, context.getString(R.string.send_mail)));
   }

}
