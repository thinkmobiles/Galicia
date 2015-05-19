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
import com.galicia.galicia.untils.BitmapCreator;

import java.util.HashMap;

/**
 * Created by vasia on 18.05.2015.
 */
public class ProductVideoAdapter extends BaseAdapter {

    private Context mContext;
    private Item mItem;
    private HashMap<String, Bitmap> mBitmapCache;

    public ProductVideoAdapter(Context _context, Item _item) {
        mContext = _context;
        mItem = _item;
        mBitmapCache = new HashMap<>();
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
        if (mItem.getExtraImages() != null)
            holder.ivVideoPreview.setImageBitmap(BitmapCreator.getBitmap(mItem.getExtraImages().get(position)));
        else holder.ivVideoPreview.setImageBitmap(getBitmap(BitmapCreator.getAbsolutePath(mItem.getExtraVideos().get(position))));
        holder.tvVideoTitle.setText(mItem.getExtraVideosDescripton().get(position));
        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private Bitmap getBitmap(String _filePath){
        if (!mBitmapCache.containsValue(_filePath)) {
            mBitmapCache.put(_filePath, ThumbnailUtils.createVideoThumbnail(_filePath,
                    MediaStore.Images.Thumbnails.MINI_KIND));
        }
        return mBitmapCache.get(_filePath);
    }


    private class Holder {
        private ImageView ivVideoPreview;
        private TextView tvVideoTitle;

    }

}
