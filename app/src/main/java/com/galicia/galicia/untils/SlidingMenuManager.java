package com.galicia.galicia.untils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.fragments.StartMenu;
import com.galicia.galicia.global.ApiManager;
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
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;
    private List<String> mMenuTitle;
    private String mTitleMenu;

    public void initMenu(Activity _activity) {
        activity = (MainActivity) _activity;
        menu = new SlidingMenu(_activity);

        makeLitener();
        ApiManager.getFirstLevel(mMenuListener);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setBehindWidthRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        menu.setSlidingEnabled(true);

        footer = View.inflate(activity,R.layout.slidemenu_footer,null);
        header = View.inflate(activity,R.layout.slidemenu_header,null);
        listMenu = (ListView) menu.findViewById(R.id.sidemenu);

        MenuAdapter menuAdapter = new MenuAdapter(mMenuTitle, activity);
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
        FragmentReplacer.popSupBackStack(activity);
        if(view == header){
            FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu());
            menu.toggle();
//        }else if(view == footer){
//            FragmentReplacer.replaceFragmentWithStack(activity, new ShoppingCartFragment());
//            menu.toggle();
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
//                case 8:
//                    FragmentReplacer.replaceFragmentWithStack(activity, new ShoppingCartFragment());
//                    menu.toggle();
//                    break;
            }
        }
    }

    private void makeLitener(){
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(activity, event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
                        createMenu();
                        break;
                }
            }
        };
    }

    private void createMenu(){
        mMenuTitle = new ArrayList<>();
        mMenuItemList = ApiManager.getFirstList();
        for (Item item: mMenuItemList){
            mMenuTitle.add(item.getName());
        }
    }
}
