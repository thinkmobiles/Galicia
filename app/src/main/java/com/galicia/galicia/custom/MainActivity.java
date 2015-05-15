package com.galicia.galicia.custom;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.galicia.galicia.R;
import com.galicia.galicia.custom.custom_list.ItemBeverage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;



public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private LinearLayout containerForAdd;
    private ArrayList<GroupBeverageModel> groupBeverageModels;
    private List<ItemBeverage> views;
    private View selectedView;
    private AtomicBoolean stateListExpand = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerForAdd = (LinearLayout) findViewById(R.id.llContentList_AM);
        prepapreMockData();
        addViews();
    }

    private void prepapreMockData(){
        groupBeverageModels = new ArrayList<>();
        groupBeverageModels.add(new GroupBeverageModel("CERVEZAS HIJOS DE RIVERA", GroupBeverageModel.mockListData1()));
        groupBeverageModels.add(new GroupBeverageModel("CERVEZAS DE IMPORTACION", GroupBeverageModel.mockListData2()));
        groupBeverageModels.add(new GroupBeverageModel("AGUAS", GroupBeverageModel.mockListData3()));
        groupBeverageModels.add(new GroupBeverageModel("VINO / LICORES", GroupBeverageModel.mockListData1()));
        groupBeverageModels.add(new GroupBeverageModel("REFRESCOS / ZUMOS / BATIDOS", GroupBeverageModel.mockListData1()));
        groupBeverageModels.add(new GroupBeverageModel("SIDRAS", GroupBeverageModel.mockListData2()));
        groupBeverageModels.add(new GroupBeverageModel("ENERGETICAS", GroupBeverageModel.mockListData3()));
    }

    private void addViews(){
        views = new ArrayList<>();
        for (GroupBeverageModel gbm: groupBeverageModels){
            final ItemBeverage ib = new ItemBeverage(this, gbm);
            ib.setOnClickListener(this);
            views.add(ib);
            containerForAdd.addView(ib);
        }
    }

    @Override
    public void onClick(View view) {
        if (selectedView == null || selectedView == view)
            stateListExpand.set(!stateListExpand.get());

        if (stateListExpand.get()){
            for (ItemBeverage ib: views){
                if (ib.title == view) {
                    ib.expandDescription();
                    ib.hideTitle(true);
                }
                else {
                    if (ib.title == selectedView)
                        ib.collapseDescription();
                    ib.hideTitle(false);
                }
            }
            selectedView = view;
        } else {
            for (ItemBeverage ib: views){
                ib.showTitle();
                if (ib.title == view)
                    ib.collapseDescription();
            }
            selectedView = null;
        }
    }
}
