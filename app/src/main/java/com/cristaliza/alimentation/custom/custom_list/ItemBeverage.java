package com.cristaliza.alimentation.custom.custom_list;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.custom.GroupBeverageModel;

public class ItemBeverage extends LinearLayout {

    private int MIN_SIZE_DESC = 0;
    private int MAX_SIZE_DESC = 0;
    private int MIN_SIZE_TITLE = 0;
    private int MAX_SIZE_TITLE = 0;

    public TextView title;
    public ItemListBeverage description;
    public View view;
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
//        view        = (View)                findViewById(R.id.gradientView);

        title.setText(gbm.title);
        description.updateContent(gbm.beverageModels, _Listener);
    }

    public void expandDescription() {
        if (!descriptionIsShowed) {
            AnimationUtils.expand(description, MIN_SIZE_DESC, MAX_SIZE_DESC, 300);
//            AnimationUtils.expand(view, MIN_SIZE_DESC, MAX_SIZE_DESC, 300);
            descriptionIsShowed = true;
        }
    }

    public void collapseDescription() {
        if (descriptionIsShowed) {
            AnimationUtils.collapse(description, MIN_SIZE_DESC, MAX_SIZE_DESC, 300, true);
//            AnimationUtils.collapse(view, MIN_SIZE_DESC, MAX_SIZE_DESC, 300, true);
            descriptionIsShowed = false;

        }
    }

    public void hideTitle(final boolean selected){
        if (selected)
            title.setBackgroundColor(0x70000000);
        else title.setBackgroundColor(0x00000000);
        if (titleIsShowed) {
            AnimationUtils.collapse(title, MIN_SIZE_TITLE, MAX_SIZE_TITLE, 200, false);
            titleIsShowed = false;
        }
    }

    public void showTitle(){
        title.setBackgroundColor(0x00000000);
        if (!titleIsShowed) {
            AnimationUtils.expand(title, MIN_SIZE_TITLE, MAX_SIZE_TITLE, 200);
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
