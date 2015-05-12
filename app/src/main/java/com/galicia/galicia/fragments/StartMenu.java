package com.galicia.galicia.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.galicia.galicia.R;
import com.galicia.galicia.adapters.ExpandMenuAdapter;
import com.galicia.galicia.untils.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 08.05.2015.
 */
public class StartMenu extends Fragment{
    private ExpandableListView mListMenu;
    private HorizontalListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_menu, container, false);
        mListMenu = (ExpandableListView) view.findViewById(R.id.lvExpandStartMenu);

        List<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();

        ArrayList<String> children1 = new ArrayList<String>();
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");
        children1.add("Child_1");

        children1.add("Child_2");
        groups.add(children1);
        groups.add(children1);
        groups.add(children1);
        groups.add(children1);
        groups.add(children1);
        groups.add(children1);
        groups.add(children1);
        groups.add(children1);


        ArrayList<String> mMenuItemList = new ArrayList<String>();
        mMenuItemList.add("Cervezas hijos de rivera");
        mMenuItemList.add("Cervezas de importacion");
        mMenuItemList.add("Aguas");
        mMenuItemList.add("vino/licores");
        mMenuItemList.add("zumos");
        mMenuItemList.add("Sidras");
        mMenuItemList.add("energeticas");


        ExpandMenuAdapter expandMenuAdapter = new ExpandMenuAdapter(getActivity(), mMenuItemList, groups);
        mListMenu.setAdapter(expandMenuAdapter);
        mListMenu.setScrollContainer(false);
//
//        HorizontalMenuAdapter adapter = new HorizontalMenuAdapter(getActivity(), children2);
//        listView.setAdapter(adapter);
        return view;
    }





}
