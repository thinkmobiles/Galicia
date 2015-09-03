package com.cristaliza.alimentation.custom.custom_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.global.ApiManager;

import java.util.List;

public class ItemListBeverage extends RelativeLayout {
    private LinearLayout llContainer;
    private int MAX_PHYSICAL_WIDTH = 0;
    private int ITEM_WIDTH = 0;

    public ItemListBeverage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ITEM_WIDTH = (int) getResources().getDimension(R.dimen.beverage_item_w)
                + 2 * (int) getResources().getDimension(R.dimen.beverage_item_margin_right_left);
        this.MAX_PHYSICAL_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        prepareViews();
    }

    public void prepareViews() {
        llContainer = new LinearLayout(getContext());
        llContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llContainer.setOrientation(LinearLayout.HORIZONTAL);
    }

    public void updateContent(final List<Item> beverageModels, OnClickListener _ClickListener) {
        LayoutParams params = new LayoutParams((int) getResources().getDimension(R.dimen.beverage_item_w_b), ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);

        llContainer.removeAllViews();
        for (Item bm : beverageModels) {
            final ImageView iv = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.beverage_horizontal_list_item, llContainer, false);
            setImage(iv, bm.getIcon());
            iv.setTag(bm);
            iv.setOnClickListener(_ClickListener);
            llContainer.addView(iv);
        }

//        if (beverageModels.size() * ITEM_WIDTH > MAX_PHYSICAL_WIDTH)
            prepareScrollParent();
//        else prepareRelativeParent();

    }

    private void prepareScrollParent() {

        HorizontalScrollView hsv = new HorizontalScrollView(getContext());
        hsv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        hsv.setHorizontalScrollBarEnabled(false);
        hsv.addView(llContainer);
        addView(hsv);

    }

    private void prepareRelativeParent() {
        addView(llContainer);

    }

    private Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    private void setImage(ImageView view, String _path) {
        int heightList = (int) getResources().getDimension(R.dimen.beverage_description_size) + 20;
        int marginLR = (int) getResources().getDimension(R.dimen.beverage_item_margin_right_left);
        int marginTB = (int) getResources().getDimension(R.dimen.beverage_item_margin_top_bottom);
        if (_path != null) {
            if (_path.isEmpty())
                view.setImageResource(R.drawable.default_bytulka);
            else {
                Bitmap bitmap = getBitmap(_path);
                view.setImageBitmap(bitmap);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        bitmap.getWidth() * heightList / bitmap.getHeight(),
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
                params.setMargins(marginLR, marginTB, marginLR, marginTB);
                view.setLayoutParams(params);
            }
        } else {
            view.setImageResource(R.drawable.default_bytulka);
        }

        ITEM_WIDTH = view.getMeasuredWidth()
                + 2 * (int) getResources().getDimension(R.dimen.beverage_item_margin_right_left);

    }

}
