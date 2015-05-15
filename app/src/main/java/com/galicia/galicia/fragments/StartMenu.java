package com.galicia.galicia.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.ExpandMenuAdapter;
import com.galicia.galicia.custom.GroupBeverageModel;
import com.galicia.galicia.custom.custom_list.ItemBeverage;
import com.galicia.galicia.global.ApiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Bogdan on 08.05.2015.
 */
public class StartMenu extends Fragment implements View.OnClickListener {

    private LinearLayout containerForAdd;
    private List<GroupBeverageModel> groupBeverageModels;
    private List<ItemBeverage> views;
    private View selectedView;
    private AtomicBoolean stateListExpand = new AtomicBoolean(false);




    private ExpandableListView mListMenu;
    private ExpandMenuAdapter mMenuAdapter;
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;
    private String mTitleMenu;

    private List<List<Item>> groups;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        containerForAdd = (LinearLayout) view.findViewById(R.id.llContentList_AM);
        makeListeners();
        ApiManager.getFirstLevel(mMenuListener);
        return view;
    }

    private void makeListeners() {
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
                        createMenu();
                        break;
                    case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
                        createSubMenu();
                        break;
                }
            }
        };
    }

    private void createMenu() {
        mMenuItemList = ApiManager.getFirstList();
        groupBeverageModels = new ArrayList<>();

        for (Item item : ApiManager.getFirstList()){
            mTitleMenu = item.getName();
            ApiManager.getSecondLevel(mMenuListener, item);

        }
        addViews();
//        mMenuAdapter = new ExpandMenuAdapter(getActivity(), mMenuItemList, groups);
//        mListMenu.setAdapter(mMenuAdapter);
//        for (Item item : ApiManager.getFirstList()){
//
//            groupBeverageModels = new ArrayList<>();
//            groupBeverageModels.add();
//        }
    }

    private void createSubMenu() {
//        List<Item> mSubMenuList = new ArrayList<>();
//        for (Item item : ApiManager.getSecondList()){
//            mSubMenuList.add(item.getName());
//        }
        groupBeverageModels.add(new GroupBeverageModel(mTitleMenu, ApiManager.getSecondList()));
    }

    //////////////////////////////////////////////////////////
//    private void prepapreMockData(){
//        groupBeverageModels = new ArrayList<>();
//        groupBeverageModels.add(new GroupBeverageModel("CERVEZAS HIJOS DE RIVERA", GroupBeverageModel.mockListData1()));
//        groupBeverageModels.add(new GroupBeverageModel("CERVEZAS DE IMPORTACION", GroupBeverageModel.mockListData2()));
//        groupBeverageModels.add(new GroupBeverageModel("AGUAS", GroupBeverageModel.mockListData3()));
//        groupBeverageModels.add(new GroupBeverageModel("VINO / LICORES", GroupBeverageModel.mockListData1()));
//        groupBeverageModels.add(new GroupBeverageModel("REFRESCOS / ZUMOS / BATIDOS", GroupBeverageModel.mockListData1()));
//        groupBeverageModels.add(new GroupBeverageModel("SIDRAS", GroupBeverageModel.mockListData2()));
//        groupBeverageModels.add(new GroupBeverageModel("ENERGETICAS", GroupBeverageModel.mockListData3()));
//    }

    private void addViews(){
        views = new ArrayList<>();
        for (GroupBeverageModel gbm: groupBeverageModels){
            final ItemBeverage ib = new ItemBeverage(getActivity(), gbm);
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

