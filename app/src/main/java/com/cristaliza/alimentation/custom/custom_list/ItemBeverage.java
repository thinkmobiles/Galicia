package com.cristaliza.alimentation.custom.custom_list;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.custom.GroupBeverageModel;

public class ItemBeverage extends LinearLayout {

    private int MIN_SIZE_DESC = 0;
    private int MAX_SIZE_DESC = 0;
    private int MIN_SIZE_TITLE = 0;
    private int MAX_SIZE_TITLE = 0;
    private static final int DURING_ANIM = 300;

    public TextView title;
    public ItemListBeverage description;
    public RelativeLayout descr;
    private boolean titleIsShowed = true, descriptionIsShowed = false;

    private GroupBeverageModel gbm;

    public ItemBeverage(Context context, GroupBeverageModel gbm, OnClickListener listener) {
        super(context);
        this.gbm = gbm;
        this.MAX_SIZE_DESC = (int) context.getResources().getDimension(R.dimen.beverage_description_size);
        this.MIN_SIZE_TITLE = (int) context.getResources().getDimension(R.dimen.beverage_title_min_size);
        this.MAX_SIZE_TITLE = (int) context.getResources().getDimension(R.dimen.beverage_title_max_size);

        inflate(context, R.layout.beverage_item, this);
        findViews(listener);
    }

    private void findViews(OnClickListener _Listener){
        title       = (TextView)            findViewById(R.id.tvTitle_BI);
        description = (ItemListBeverage)    findViewById(R.id.vDescription_BI);
        descr       = (RelativeLayout)      findViewById(R.id.rlDescr);

        title.setText(gbm.title);
        description.updateContent(gbm.beverageModels, _Listener);
    }

    public void expandDescription() {
        if (!descriptionIsShowed) {
            AnimationUtils.expand(description, MIN_SIZE_DESC, MAX_SIZE_DESC, DURING_ANIM);
            AnimationUtils.expand(descr, MIN_SIZE_DESC, MAX_SIZE_DESC, DURING_ANIM);
            descriptionIsShowed = true;
        }
    }
    public void collapseDescription() {
        if (descriptionIsShowed) {
            AnimationUtils.collapse(description, MIN_SIZE_DESC, MAX_SIZE_DESC, DURING_ANIM, true);
            AnimationUtils.collapse(descr, MIN_SIZE_DESC, MAX_SIZE_DESC, DURING_ANIM, true);
            descriptionIsShowed = false;
        }
    }
    public void hideTitle(final boolean selected){
        if (selected) {
            title.setBackgroundColor(0x70000000);
            AnimationUtils.expand(title, MAX_SIZE_TITLE, MIN_SIZE_TITLE, DURING_ANIM);
        } else {
            title.setBackgroundColor(0x00000000);
            AnimationUtils.expand(title, MIN_SIZE_TITLE, MAX_SIZE_TITLE, DURING_ANIM);
        }
        if (titleIsShowed) {
            titleIsShowed = false;
        }
    }
    public void showTitle(){
        title.setBackgroundColor(0x00000000);
        if (!titleIsShowed) {
            AnimationUtils.expand(title, MAX_SIZE_TITLE, MAX_SIZE_TITLE, DURING_ANIM);
            titleIsShowed = true;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        title.setOnClickListener(l);
    }
}
