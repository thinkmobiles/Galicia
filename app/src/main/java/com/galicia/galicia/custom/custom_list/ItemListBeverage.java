package com.galicia.galicia.custom.custom_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ApiManager;

import java.util.List;


/**
 * Created by Michael on 14.05.2015.
 */
public class ItemListBeverage extends RelativeLayout {
    private LinearLayout llContainer;
    private int MAX_PHYSICAL_WIDTH = 0;
    private int ITEM_WIDTH = 0;

    public ItemListBeverage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ITEM_WIDTH         = (int) context.getResources().getDimension(R.dimen.beverage_item_w);
        this.MAX_PHYSICAL_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        prepareViews();
    }

    public void prepareViews(){
        llContainer = new LinearLayout(getContext());
        llContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llContainer.setOrientation(LinearLayout.HORIZONTAL);
    }

    public void updateContent(final List<Item> beverageModels){
        llContainer.removeAllViews();
        for (Item bm: beverageModels){
            final ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.beverage_horizontal_list_item, llContainer, false);
            iv.setImageBitmap(getBitmap(bm.getIcon()));
//            Picasso.with(getContext()).load(bm.getIcon()).placeholder(R.drawable.default_bytulka).error(R.drawable.default_bytulka).into(iv);
            llContainer.addView(iv);
        }

        if (beverageModels.size() * ITEM_WIDTH > MAX_PHYSICAL_WIDTH)
            prepareScrollParent();
        else prepareRelativeParent();
    }

    private void prepareScrollParent(){
        HorizontalScrollView hsv = new HorizontalScrollView(getContext());
        hsv.setLayoutParams(new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hsv.addView(llContainer);
        addView(hsv);
    }

    private void prepareRelativeParent(){
        addView(llContainer);
    }

    private Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

}
