package com.galicia.galicia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.R;
import com.galicia.galicia.untils.BitmapCreator;

import java.util.List;

/**
 * Created by vasia on 18.05.2015.
 */
public class HorizontalPhotoProductAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mProductList;
    private int mItemMargin = 0;

    public HorizontalPhotoProductAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    public void setItemMargin(final int _itemMargin){
        mItemMargin = _itemMargin;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_photo_horizontal, null);
            holder = new Holder();
            holder.ivImage = (ImageView) convertView.findViewById(R.id.ivIcon_IPI);
            holder.tvProductName = (TextView) convertView.findViewById(R.id.tvDescription_IPI);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (mItemMargin != 0) {
            final RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rlParams.setMargins(mItemMargin, 0, mItemMargin, 40);
            holder.ivImage.setLayoutParams(rlParams);
        }

        holder.ivImage.setImageBitmap(BitmapCreator.getBitmap(mProductList.get(position).getImage()));
//        holder.tvProductName.setText(mProductList.get(position).getName());

        return convertView;
    }


    private class Holder {
        private ImageView ivImage;
        private TextView tvProductName;
    }

}
