package com.cristaliza.nacional.custom.custom_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.nacional.R;
import com.cristaliza.nacional.global.ApiManager;

import java.util.List;

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
        llContainer.setGravity(CENTER_HORIZONTAL);
    }

    public void updateContent(final List<Item> beverageModels, OnClickListener _ClickListener){
        llContainer.removeAllViews();
        for (Item bm: beverageModels){
            final ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.beverage_horizontal_list_item, llContainer, false);
            setImage(iv, bm.getIcon());
            iv.setTag(bm);
            iv.setOnClickListener(_ClickListener);
            llContainer.addView(iv);
        }

//        if (beverageModels.size() * ITEM_WIDTH > MAX_PHYSICAL_WIDTH)
            prepareScrollParent();
//        else prepareRelativeParent();
//        final float scale = getResources().getDisplayMetrics().density;
//        int my_dp = (int) (20 * scale + 0.5f);
//        View view = new View(getContext());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(my_dp,ViewGroup.LayoutParams.MATCH_PARENT);
//        params.gravity = Gravity.END;
//        view.setLayoutParams(params);
//        view.setBackgroundResource(R.drawable.horizontal_gradient_shape);
//        addView(view);

    }

    private void prepareScrollParent(){

        HorizontalScrollView hsv = new HorizontalScrollView(getContext());
        hsv.setLayoutParams(new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hsv.setHorizontalScrollBarEnabled(false);
        hsv.addView(llContainer);
        addView(hsv);

    }

    private void prepareRelativeParent(){
        addView(llContainer);

    }

    private Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    private void setImage(ImageView view, String _path) {
        int koef = (int) getResources().getDimension(R.dimen.beverage_description_size) + 20;
        int marginLeftRight = (int) getResources().getDimension(R.dimen.beverage_item_margin_right_left);
        int marginTopBottom = (int) getResources().getDimension(R.dimen.beverage_item_margin_top_bottom);
        if (_path != null && !_path.isEmpty()){
            Bitmap bitmap = getBitmap(_path);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    bitmap.getWidth() * koef / bitmap.getHeight(),
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
