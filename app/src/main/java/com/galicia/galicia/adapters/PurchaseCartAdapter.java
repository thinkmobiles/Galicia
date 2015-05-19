package com.galicia.galicia.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ItemsPurchaseList;
import com.galicia.galicia.global.ProgressDialogWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class PurchaseCartAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private ArrayList<Item> itemsData;
    private LayoutInflater inflater;



    public PurchaseCartAdapter(Context _context, ArrayList<Item> _data){
        if(_context != null){
            context = _context;
            itemsData = _data;

            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }
    @Override
    public int getCount() {
        return itemsData.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_shoping_cart_list,parent,false);
            holder = new ViewHolder();

            holder.previewImage = (ImageView) convertView.findViewById(R.id.ivPrevProduct);
            holder.nameItem = (TextView) convertView.findViewById(R.id.tv_title_goods_IS);
            holder.refreshButton = (ImageView) convertView.findViewById(R.id.iv_refresh_shop_item_IS);
            holder.deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_shop_item_IS);
            holder.seeButton = (ImageView) convertView.findViewById(R.id.iv_see_detail_shop_item_IS);
            holder.sendButton = (ImageView) convertView.findViewById(R.id.iv_send_shop_item_IS);

            convertView.setTag(holder);

        }else
            holder = (ViewHolder) convertView.getTag();

            holder.previewImage.setImageDrawable(getDrawable(itemsData.get(position).getIcon()));
            holder.nameItem.setText(itemsData.get(position).getName());

            holder.sendButton.setOnClickListener(this);
            holder.deleteButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemsPurchaseList.getInstance(context).deleteItem(position);
                    notifyDataSetChanged();
                }
            });

            holder.refreshButton.setOnClickListener(this);
            holder.seeButton.setOnClickListener(this);

        return convertView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_send_shop_item_IS :
                break;
            case R.id.iv_refresh_shop_item_IS :
                break;
            case R.id.iv_see_detail_shop_item_IS :
                break;
        }
    }

    class ViewHolder{
        private TextView nameItem;
        private ImageView previewImage, refreshButton, deleteButton, seeButton, sendButton;
    }

    public Drawable getDrawable(String _icon){
        return Drawable.createFromPath(_icon);
    }
}
