package com.galicia.galicia.custom.custom_list;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.galicia.galicia.R;
import com.galicia.galicia.custom.GroupBeverageModel;

public class ItemBeverage extends LinearLayout {

    private int MIN_SIZE_DESC = 0;
    private int MAX_SIZE_DESC = 0;
    private int MIN_SIZE_TITLE = 0;
    private int MAX_SIZE_TITLE = 0;

    public TextView title;
    public ItemListBeverage description;
    private RelativeLayout descr;
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
        descr       = (RelativeLayout)      findViewById(R.id.rlDescr_BI);

        title.setText(gbm.title);
        description.updateContent(gbm.beverageModels, _Listener);
    }

    public void expandDescription() {
        if (!descriptionIsShowed) {
            AnimationUtils.expand(description, MIN_SIZE_DESC, MAX_SIZE_DESC, 300);
            AnimationUtils.expand(descr, MIN_SIZE_DESC, MAX_SIZE_DESC, 300);
//            AnimationUtils.expand(view, MIN_SIZE_DESC, MAX_SIZE_DESC, 300);
            descriptionIsShowed = true;
        }
    }

    public void collapseDescription() {
        if (descriptionIsShowed) {
            AnimationUtils.collapse(description, MIN_SIZE_DESC, MAX_SIZE_DESC, 300, true);
            AnimationUtils.collapse(descr, MIN_SIZE_DESC, MAX_SIZE_DESC, 300, true);
            descriptionIsShowed = false;

        }
    }

    public void hideTitle(final boolean selected){
        if (selected) {
            title.setBackgroundColor(0x70000000);
            AnimationUtils.expand(title, MAX_SIZE_TITLE, MIN_SIZE_TITLE, 200);
        } else {
            title.setBackgroundColor(0x00000000);
            AnimationUtils.expand(title, MIN_SIZE_TITLE, MAX_SIZE_TITLE, 200);
        }
        if (titleIsShowed) {
//            AnimationUtils.collapse(title, MIN_SIZE_TITLE, MAX_SIZE_TITLE, 200, false);
            titleIsShowed = false;
        }
    }

    public void showTitle(){
        title.setBackgroundColor(0x00000000);
        if (!titleIsShowed) {
            AnimationUtils.expand(title, MAX_SIZE_TITLE, MAX_SIZE_TITLE, 200);
            titleIsShowed = true;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        title.setOnClickListener(l);
    }

    public String getTitleName(){
        return (String) title.getText();
    }
}
