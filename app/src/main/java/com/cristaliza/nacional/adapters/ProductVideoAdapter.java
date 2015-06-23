package com.cristaliza.nacional.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.nacional.R;
import com.cristaliza.nacional.global.Constants;
import com.cristaliza.nacional.untils.BitmapCreator;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by vasia on 18.05.2015.
 */
public class ProductVideoAdapter extends BaseAdapter {

    private Context mContext;
    private Item mItem;
    private HashMap<String, Bitmap> mExtraVideoBitmapCache;

    public ProductVideoAdapter(Context _context, Item _item) {
        mContext = _context;
        mItem = _item;
        mExtraVideoBitmapCache = new HashMap<>();
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
            holder.ivExtraVideo = (ImageView) convertView.findViewById(R.id.ivVideoImage_IPV);
            holder.tvVideoDescription = (TextView) convertView.findViewById(R.id.tvVideoTitle_IPV);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (mItem.getExtraImages() != null)
            holder.ivExtraVideo.setImageBitmap(BitmapCreator.getBitmap(mItem.getExtraImages().get(position)));
        else
            Picasso.with(mContext)
                    .load(Constants.URL_YOUTUBE_IMG + getYouTubeImageId(mItem.getExtraVideos().get(position)) + Constants.URL_YOUTUBE_IMG_INDEX)
                    .into(holder.ivExtraVideo);

         // .setImageBitmap(getBitmapFromExtraVideo(BitmapCreator.getAbsolutePath()));

        holder.tvVideoDescription.setText(mItem.getExtraVideosDescripton().get(position));
        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private Bitmap getBitmapFromExtraVideo(String _filePath){
        if (!mExtraVideoBitmapCache.containsKey(_filePath)) {
            mExtraVideoBitmapCache.put(_filePath, ThumbnailUtils.createVideoThumbnail(_filePath,
                    MediaStore.Images.Thumbnails.MINI_KIND));
        }
        return mExtraVideoBitmapCache.get(_filePath);
    }

    public static String getYouTubeImageId(String str){
        int start_index = str.lastIndexOf("/")+1;
        int end_index = str.length();
        String videoId = str.substring(start_index,end_index);
        Log.i("TER",videoId);
        return  videoId;
    }


    private class Holder {
        private ImageView ivExtraVideo;
        private TextView tvVideoDescription;

    }

}
