package com.galicia.galicia.untils;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.galicia.galicia.R;
import com.galicia.galicia.fragments.ShoppingCartFragment;
import com.galicia.galicia.global.FragmentReplacer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 06.05.2015.
 */
public class SlidingMenuManager {

    private SlidingMenu menu;
    private Activity activity;
    private BaseAdapter adapter;
    private ListView listMenu;

    public void initMenu(final Activity _activity) {
        activity = (Activity) _activity;
        menu = new SlidingMenu(_activity);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setBehindWidthRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);

        listMenu = (ListView) menu.findViewById(R.id.sidemenu);
        List<String> strings = new ArrayList<>();
        strings.add("Item 1 ");
        strings.add("Item 2 ");
        strings.add("Item 3 ");
        strings.add("Item 4");
        strings.add("Item 5 ");
        MenuAdapter menuAdapter = new MenuAdapter(strings, activity);
        listMenu.setAdapter(menuAdapter);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 4:
                        FragmentReplacer.replaceTopNavigationFragment(_activity, new ShoppingCartFragment());
                        break;

                }
            }
        });
    }

    public void show(){
        menu.showMenu();
    }
}
