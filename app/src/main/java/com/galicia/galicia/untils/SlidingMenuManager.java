package com.galicia.galicia.untils;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.galicia.galicia.R;
import com.galicia.galicia.fragments.ShoppingCartFragment;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.fragments.FragmentSlide;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 06.05.2015.
 */
public class SlidingMenuManager implements AdapterView.OnItemClickListener {

    private SlidingMenu menu;
    private ActionBarActivity activity;
    private BaseAdapter adapter;
    private MenuAdapter menuAdapter;
    private ListView listMenu;
    private View footer, header;

    public void initMenu(Activity _activity) {
        activity = (ActionBarActivity) _activity;
        menu = new SlidingMenu(_activity);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setBehindWidthRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);

        footer = View.inflate(activity,R.layout.slidemenu_footer,null);
        header = View.inflate(activity,R.layout.slidemenu_header,null);
        listMenu = (ListView) menu.findViewById(R.id.sidemenu);
        List<String> strings = new ArrayList<>();
        strings.add("Item 1 ");
        strings.add("Item 2 ");
        strings.add("Item 3 ");
        strings.add("Item 4");
        strings.add("Item 5 ");
        MenuAdapter menuAdapter = new MenuAdapter(strings, activity);
        listMenu.setAdapter(menuAdapter);
        listMenu.addFooterView(footer);
        listMenu.addHeaderView(header);

        listMenu.setOnItemClickListener(this);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 4:
                        FragmentReplacer.replaceTopNavigationFragment(activity, new ShoppingCartFragment());
                        break;

                }
            }
        });
    }

    public void show(){
        menu.showMenu();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view == header){
            Log.e("listener","header");
        }else if(view == footer){
            Log.e("listener","footer");
        } else{
            switch (position-1){
                case 0:
                    Log.e("listener","0");
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSlide()).commit();
                    break;
                case 1:
                    Log.e("listener","1");
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSlide()).commit();
                    break;
                case 2:
                    Log.e("listener","2");
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentSlide()).commit();
                    break;
            }
        }
    }
}
