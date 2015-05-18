package com.galicia.galicia.untils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;

import com.galicia.galicia.fragments.PurchaseCartFragment;

import com.galicia.galicia.fragments.StartMenu;
import com.galicia.galicia.global.FragmentReplacer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 06.05.2015.
 */
public class SlidingMenuManager implements AdapterView.OnItemClickListener {

    private SlidingMenu menu;
    private MainActivity activity;
    private BaseAdapter adapter;
    private MenuAdapter menuAdapter;
    private ListView listMenu;
    private View footer, header;

    public void initMenu(Activity _activity) {
        activity = (MainActivity) _activity;
        menu = new SlidingMenu(_activity);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setBehindWidthRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        menu.setSlidingEnabled(true);

        footer = View.inflate(activity,R.layout.slidemenu_footer,null);
        header = View.inflate(activity,R.layout.slidemenu_header,null);
        listMenu = (ListView) menu.findViewById(R.id.sidemenu);
        List<String> strings = new ArrayList<>();
        strings.add(activity.getString(R.string.title_rivera));
        strings.add(activity.getString(R.string.title_importacion));
        strings.add(activity.getString(R.string.title_aguas));
        strings.add(activity.getString(R.string.title_vino));
        strings.add(activity.getString(R.string.title_refrescos));
        strings.add(activity.getString(R.string.title_sidras));
        strings.add(activity.getString(R.string.title_energetical));
        MenuAdapter menuAdapter = new MenuAdapter(strings, activity);
        listMenu.addHeaderView(header);
        listMenu.addFooterView(footer);
        listMenu.setAdapter(menuAdapter);

        listMenu.setOnItemClickListener(this);
    }

    public void show(){
        menu.showMenu();
    }
    public void enableMenu(final boolean _state){
        menu.setSlidingEnabled(_state);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view == header){
            FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu());
            menu.toggle();
        }else if(view == footer){
            FragmentReplacer.replaceFragmentWithStack(activity, new PurchaseCartFragment());
            menu.toggle();
        } else{
            switch (position){
                case 0:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu());
                    menu.toggle();
                    break;
                case 1:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(0));
                    menu.toggle();
                    break;
                case 2:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(1));
                    menu.toggle();
                    break;
                case 3:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(2));
                    menu.toggle();
                    break;
                case 4:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(3));
                    menu.toggle();
                    break;
                case 5:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(4));
                    menu.toggle();
                    break;
                case 6:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(5));
                    menu.toggle();
                    break;
                case 7:
                    FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu().newInstance(6));
                    menu.toggle();
                    break;
                case 8:
                    FragmentReplacer.replaceFragmentWithStack(activity, new PurchaseCartFragment());
                    menu.toggle();
                    break;
            }
        }
    }
}
