package com.galicia.galicia.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.galicia.galicia.R;
import com.galicia.galicia.global.ProgressDialogWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ShoppingCartAdapter extends BaseAdapter {
    private Context context;
    private List<String> titles;
    private LayoutInflater inflater;
    private Activity activity;

    public ShoppingCartAdapter(Activity _activity, Context _context, List<String> _data){
        if(_context != null){
            activity = _activity;
            context = _context;
            titles = _data;

            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }
    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_shoping_cart_list,parent,false);
            holder = new ViewHolder();

            holder.titleGoods = (TextView) convertView.findViewById(R.id.tv_title_goods_IS);
            holder.refreshButton = (ImageView) convertView.findViewById(R.id.iv_refresh_shop_item_IS);
            holder.deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_shop_item_IS);
            holder.seeButton = (ImageView) convertView.findViewById(R.id.iv_see_detail_shop_item_IS);
            holder.sendButton = (ImageView) convertView.findViewById(R.id.iv_send_shop_item_IS);

            convertView.setTag(holder);

        }else
            holder = (ViewHolder) convertView.getTag();

            holder.titleGoods.setText(titles.get(position).toString());
            holder.sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  showDialog();
                }
            });

        return convertView;
    }

    class ViewHolder{
        private TextView titleGoods;
        private ImageView refreshButton, deleteButton, seeButton, sendButton;
    }

    private void showDialog(){
        final AlertDialog.Builder spinerDialog = new AlertDialog.Builder(activity);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_spinner,null);

        Spinner spinner = (Spinner) view.findViewById(R.id.dialogSpinner);
        // spinner.
        spinner.setAdapter(ArrayAdapter.createFromResource(context, R.array.spinner_resource,R.layout.item_spiner));
        spinerDialog.setView(view);
        AlertDialog alertDialog = spinerDialog.create();

        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);
        alertDialog.getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.show();

    }
}
