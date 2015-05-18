package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.custom.GroupBeverageModel;
import com.galicia.galicia.custom.custom_list.ItemBeverage;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.ItemSerializable;

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
    private MainActivity mainActivity;




    private EventListener mMenuListener;
    private List<Item> mMenuItemList;
    private String mTitleMenu, mBaseTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

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
        groupBeverageModels = new ArrayList<>();
        mMenuItemList = ApiManager.getFirstList();
        for (Item item : mMenuItemList){
            mTitleMenu = item.getName();
            ApiManager.getSecondLevel(mMenuListener, item);
        }
        addViews();
    }

    private void createSubMenu() {
        groupBeverageModels.add(new GroupBeverageModel(mTitleMenu, ApiManager.getSecondList()));
    }

    private void addViews(){
        views = new ArrayList<>();
            for (int i = 0; i < groupBeverageModels.size(); i++ ) {
            final ItemBeverage ib = new ItemBeverage(getActivity(), groupBeverageModels.get(i), mItemOnClickListener);
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

    private View.OnClickListener mItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Item item = (Item) v.getTag();
            openItemFragment(item);
        }
    };

    private void openItemFragment(final Item _item) {
        ItemSerializable itemSerializable = new ItemSerializable();
        itemSerializable.setItem(_item);
        if (_item.getExtraVideos() != null && !_item.getExtraVideos().isEmpty()){
            FragmentReplacer.replaceFragmentWithStack(mainActivity, FragmentProductDetail.newInstance(itemSerializable));
        }
        else if (_item.getExtraImages() != null && !_item.getExtraImages().isEmpty()){
            FragmentReplacer.replaceFragmentWithStack(mainActivity, FragmentProductDetail.newInstance(itemSerializable));
        } else {
            FragmentReplacer.replaceFragmentWithStack(mainActivity, FragmentProductNoDetail.newInstance(itemSerializable));
        }

//        FragmentReplacer.replaceFragmentWithStack(mainActivity, FragmentProductUniversal.newInstance(itemSerializable));



    }
}

