package com.galicia.galicia.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ApiManager;

import java.util.List;

/**
 * Created by vasia on 18.05.2015.
 */
public class ProductVideoApter extends BaseAdapter {

    private Context mContext;
    private Item mItem;

    public ProductVideoApter(Context _context, Item _item) {
        this.mContext = _context;
        this.mItem = _item;
    }

    @Override
    public int getCount() {
        return mItem.getExtraVideos().size();
    }

    @Override
    public Object getItem(int position) {
        return mItem.getExtraVideos().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_video, null);
            holder = new Holder();
            holder.ivVideoPreview = (ImageView) convertView.findViewById(R.id.ivVideoImage_IPV);
            holder.tvVideoTitle = (TextView) convertView.findViewById(R.id.tvVideoTitle_IPV);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.ivVideoPreview.setImageBitmap(getBitmap(mItem.getExtraVideos().get(position)));
        holder.tvVideoTitle.setText(mItem.getExtraVideosDescripton().get(position));

        return convertView;
    }



    private class Holder {
        private ImageView ivVideoPreview;
        private TextView tvVideoTitle;

    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private Bitmap getBitmap(String _path) {
        return ThumbnailUtils.createVideoThumbnail(ApiManager.getPath() + _path, MediaStore.Images.Thumbnails.MINI_KIND);
    }
}
