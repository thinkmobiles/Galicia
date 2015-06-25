package com.cristaliza.alimentation.fragments;

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
import com.cristaliza.alimentation.MainActivity;
import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.custom.GroupBeverageModel;
import com.cristaliza.alimentation.custom.custom_list.ItemBeverage;
import com.cristaliza.alimentation.global.ApiManager;
import com.cristaliza.alimentation.global.Constants;
import com.cristaliza.alimentation.global.FragmentReplacer;
import com.cristaliza.alimentation.models.ItemSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class StartMenu extends Fragment implements View.OnClickListener {

    private LinearLayout containerForAdd;
    private List<GroupBeverageModel> groupBeverageModels;
    private List<ItemBeverage> views;
    private List<Item> mMenuItemList;
    private View selectedView;
    private AtomicBoolean stateListExpand = new AtomicBoolean(false);
    private EventListener mMenuListener;

    private MainActivity mCallingActivity;
    private String mTitleMenu, mBaseTitle;
    private int idOpen = -1;

    public static StartMenu newInstance(final int _open){
        StartMenu fragment = new StartMenu();
        Bundle attr = new Bundle();
        attr.putInt(Constants.PARAM_OPEN,_open);
        fragment.setArguments(attr);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallingActivity = (MainActivity) activity;
        if(getArguments() != null){
            idOpen = getArguments().getInt(Constants.PARAM_OPEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_menu_fragment, container, false);

        containerForAdd = (LinearLayout) view.findViewById(R.id.llContentList_AM);
        mCallingActivity.setBackground();
        mCallingActivity.setTitle("");
        makeListeners();
        ApiManager.getFirstLevel(mMenuListener);
        mCallingActivity.setEnableMenu(false);
        return view;
    }

    private void makeListeners() {
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + getActivity().getString(R.string.error), Toast.LENGTH_SHORT).show();
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
        if(idOpen != -1){
            clickItem(idOpen);
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

    private void openItemFragment(final Item _item) {
        FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FragmentProduct.newInstance(new ItemSerializable(_item)));
    }

    private void clickItem(final int position) {
        ((ItemBeverage)containerForAdd.getChildAt(position)).title.performClick();
    }
}

