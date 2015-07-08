package com.cristaliza.nacional.fragments;

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
import com.cristaliza.nacional.MainActivity;
import com.cristaliza.nacional.R;
import com.cristaliza.nacional.custom.GroupBeverageModel;
import com.cristaliza.nacional.custom.custom_list.ItemBeverage;
import com.cristaliza.nacional.global.ApiManager;
import com.cristaliza.nacional.global.Constants;
import com.cristaliza.nacional.global.FragmentReplacer;
import com.cristaliza.nacional.models.ItemSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class StartMenu extends Fragment implements View.OnClickListener {
    private LinearLayout containerForAdd, llCompania;
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
        findUI(view);
        makeListeners();
        ApiManager.getFirstLevel(mMenuListener);
        return view;
    }
    private void findUI(View _view){
        containerForAdd = (LinearLayout) _view.findViewById(R.id.llContentList_AM);
        llCompania = (LinearLayout) _view.findViewById(R.id.llCompania);
        llCompania.setOnClickListener(this);
        mCallingActivity.setBackground();
        mCallingActivity.setTitle("");
        mCallingActivity.setEnableMenu(false);
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
        for (int i = 1; i <mMenuItemList.size(); ++i){
            mTitleMenu = mMenuItemList.get(i).getName();
            ApiManager.getSecondLevel(mMenuListener, mMenuItemList.get(i));
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
        if(view.getId() == R.id.llCompania){
            FragmentReplacer.replaceFragmentWithStack(
                    mCallingActivity,
                    CompaniaFragment.newInstance(new ItemSerializable(mMenuItemList.get(0)))
            );
        } else {
            if (selectedView == null || selectedView == view)
                stateListExpand.set(!stateListExpand.get());
            if (stateListExpand.get()) {
                for (ItemBeverage ib : views) {
                    if (ib.title == view) {
                        ib.expandDescription();
                        ib.hideTitle(true);
                    } else {
                        if (ib.title == selectedView)
                            ib.collapseDescription();
                        ib.hideTitle(false);
                    }
                }
                selectedView = view;
            } else {
                for (ItemBeverage ib : views) {
                    ib.showTitle();
                    if (ib.title == view)
                        ib.collapseDescription();
                }
                selectedView = null;
            }
            mBaseTitle = ((TextView) view).getText().toString();
        }
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

