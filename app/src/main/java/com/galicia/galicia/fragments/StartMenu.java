package com.galicia.galicia.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.ExpandMenuAdapter;
import com.galicia.galicia.global.ApiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 08.05.2015.
 */
public class StartMenu extends Fragment {

    private ExpandableListView mListMenu;
    private ExpandMenuAdapter mMenuAdapter;
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;

    private List<List<Item>> groups;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_menu, container, false);
        mListMenu = (ExpandableListView) view.findViewById(R.id.lvExpandStartMenu);
        groups = new ArrayList<>();

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

        for (Item item : ApiManager.getFirstList()){
            ApiManager.getSecondLevel(mMenuListener, item);
        }
        mMenuAdapter = new ExpandMenuAdapter(getActivity(), mMenuItemList, groups);
        mListMenu.setAdapter(mMenuAdapter);
    }

    private void createSubMenu() {
//        List<Item> mSubMenuList = new ArrayList<>();
//        for (Item item : ApiManager.getSecondList()){
//            mSubMenuList.add(item.getName());
//        }
        groups.add(ApiManager.getSecondList());
    }

}

