package com.galicia.galicia.custom.custom_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ApiManager;

import java.util.List;

public class ItemListBeverage extends RelativeLayout {
    private LinearLayout llContainer;

    public ItemListBeverage(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepareViews();
    }

    public void prepareViews(){
        llContainer = new LinearLayout(getContext());
        llContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llContainer.setOrientation(LinearLayout.HORIZONTAL);
    }

    public void updateContent(final List<Item> beverageModels, OnClickListener _ClickListener){
        llContainer.removeAllViews();
        for (Item bm: beverageModels){
            final ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.beverage_horizontal_list_item, llContainer, false);
            setImage(iv, bm);
            iv.setTag(bm);
            iv.setOnClickListener(_ClickListener);
            llContainer.addView(iv);
        }

        prepareScrollParent();

    }

    private void prepareScrollParent(){
        HorizontalScrollView hsv = new HorizontalScrollView(getContext());
        hsv.setLayoutParams(new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hsv.setHorizontalScrollBarEnabled(false);
        hsv.addView(llContainer);
        addView(hsv);

    }

    private Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    private void setImage(ImageView view, Item _item) {
        if (_item.getIcon() != null){
            int heightList = (int) getResources().getDimension(R.dimen.beverage_description_size) + 30;
            int marginLeftRight = (int) getResources().getDimension(R.dimen.beverage_item_margin_right_left);
            int marginTopBottom = (int) getResources().getDimension(R.dimen.beverage_item_margin);
            Bitmap bitmap = getBitmap(_item.getIcon());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    bitmap.getWidth() * heightList / bitmap.getHeight(),
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom);
            view.setImageBitmap(bitmap);
            view.setLayoutParams(params);
        } else {
            view.setImageResource(R.drawable.default_bytulka);
        }
    }

}
