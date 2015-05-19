package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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


public class StartMenu extends Fragment implements View.OnClickListener {

    private LinearLayout containerForAdd;
    private List<GroupBeverageModel> groupBeverageModels;
    private List<ItemBeverage> views;
    private View selectedView;
    private AtomicBoolean stateListExpand = new AtomicBoolean(false);
    private MainActivity mCallingActivity;




    private EventListener mMenuListener;
    private List<Item> mMenuItemList;
    private String mTitleMenu, mBaseTitle;

    private int idOpen = -1;

    public static StartMenu newInstance(final int _open){
        StartMenu fragment = new StartMenu();
        Bundle attr = new Bundle();
        attr.putInt("OPEN",_open);
        fragment.setArguments(attr);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallingActivity = (MainActivity) activity;
        if(getArguments() != null){
            idOpen = getArguments().getInt("OPEN");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        containerForAdd = (LinearLayout) view.findViewById(R.id.llContentList_AM);
        mCallingActivity.setBackground();
        mCallingActivity.setTitle("");
        makeListeners();
        ApiManager.getFirstLevel(mMenuListener);
        if(idOpen != -1){
            openItemMenu(idOpen);
        }
        mCallingActivity.setEnableMenu(false);
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
        mBaseTitle = ((TextView)view).getText().toString();
    }

    private View.OnClickListener mItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Item item = (Item) v.getTag();
            mCallingActivity.setTitle(mBaseTitle);
            closeItemMenu();
            openItemFragment(item);
        }
    };

    private void closeItemMenu(){
        for (ItemBeverage ib: views) {
            if (ib.title == selectedView) {
                ib.collapseDescription();
                ib.hideTitle(false);
            }
        }
    }

    private void openItemMenu(final int _id){
        views.get(_id).expandDescription();
        views.get(_id).hideTitle(true);
        stateListExpand.set(!stateListExpand.get());
        selectedView = views.get(_id).title;
    }

    private void openItemFragment(final Item _item) {
        mCallingActivity.setEnableMenu(true);
        ItemSerializable itemSerializable = new ItemSerializable(_item);
//
            FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FragmentProductUniversal.newInstance(itemSerializable));
//        if (_item.getExtraVideos() != null && !_item.getExtraVideos().isEmpty()){
//            FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FragmentProductDetail.newInstance(itemSerializable));
//        }
//        else if (_item.getExtraImages() != null && !_item.getExtraImages().isEmpty()){
//            FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FragmentProductDetail.newInstance(itemSerializable));
//        } else {
//            FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FragmentProductNoDetail.newInstance(itemSerializable));
//            FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FragmentProductNoDetail.newInstance(itemSerializable));
//        }
    }
}

