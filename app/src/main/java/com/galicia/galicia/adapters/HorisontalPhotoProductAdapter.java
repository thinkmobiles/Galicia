package com.galicia.galicia.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.untils.BitmapCreator;

import java.util.List;

/**
 * Created by vasia on 18.05.2015.
 */
public class HorisontalPhotoProductAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mList;
    private EventListener mListener;

    public HorisontalPhotoProductAdapter(Context mContext, List<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_image, null);
            holder = new Holder();
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon_IPI);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription_IPI);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.ivIcon.setImageBitmap(BitmapCreator.getBitmap(mList.get(position).getImage()));
        holder.tvDescription.setText(mList.get(position).getImageDescription());
//        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, mList.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return convertView;
    }





    private class Holder {
        private ImageView ivIcon;
        private TextView tvDescription;
    }

}
