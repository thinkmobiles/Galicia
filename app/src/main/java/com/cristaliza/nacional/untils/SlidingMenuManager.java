package com.cristaliza.nacional.untils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.nacional.MainActivity;
import com.cristaliza.nacional.R;
import com.cristaliza.nacional.fragments.CompaniaFragment;
import com.cristaliza.nacional.fragments.ShopCartFragment;
import com.cristaliza.nacional.fragments.StartMenu;
import com.cristaliza.nacional.global.ApiManager;
import com.cristaliza.nacional.global.FragmentReplacer;
import com.cristaliza.nacional.models.ItemSerializable;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;
public class SlidingMenuManager implements AdapterView.OnItemClickListener {
    private SlidingMenu menu;
    private MainActivity activity;
    private ListView listMenu;
    private View footer, header, compania;
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;
    private List<String> mMenuTitle;
    private MenuAdapter menuAdapter;
    private RelativeLayout mSpace;
    private TextView tvEnvios;
    public void initMenu(Activity _activity) {
        activity = (MainActivity) _activity;
        menu = new SlidingMenu(_activity);
        makeLitener();
        ApiManager.getFirstLevel(mMenuListener);
        setPropertyMenu(_activity);
        findView(menu);
        initAdapter();
        initViewListeners();
    }
    private void setPropertyMenu(Activity _activity){
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setBehindWidth(getDisplayWidth());
        menu.setFadeDegree(0.3f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        menu.setSlidingEnabled(true);
    }
    private void findView(View _view){
        footer      = View.inflate(activity,R.layout.slidemenu_footer,null);
        header      = View.inflate(activity, R.layout.slidemenu_header, null);
        compania    = View.inflate(activity, R.layout.slidemenu_compania, null);
        listMenu    = (ListView) _view.findViewById(R.id.sidemenu);
    }
    private void initAdapter(){
        menuAdapter= new MenuAdapter(mMenuTitle, activity);
        listMenu.addHeaderView(header);
        listMenu.addHeaderView(compania);
        listMenu.addFooterView(footer);
        listMenu.setAdapter(menuAdapter);
    }
    private void initViewListeners(){
        listMenu.setOnItemClickListener(this);
    }
    public void show(){
        menu.showMenu();
    }
    public void toggle(){
        menu.toggle();
    }
    public boolean isShowing(){
        return menu.isMenuShowing();
    }
    public void enableMenu(final boolean _state){
        menu.setSlidingEnabled(_state);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view == header){
            FragmentReplacer.replaceFragmentWithStack(activity, new StartMenu());
        }else if(view == footer) {
            FragmentReplacer.replaceFragmentWithStack(activity, ShopCartFragment.newInstance());
        } else if(view == compania){
            FragmentReplacer.replaceFragmentWithStack(
                    activity,
                    CompaniaFragment.newInstance(new ItemSerializable(mMenuItemList.get(0)))
            );
        } else {
            FragmentReplacer.clearSupBackStack(activity);
            FragmentReplacer.replaceFragmentWithStack(activity, StartMenu.newInstance(position - 1));
        }
        menu.toggle();
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
        for (int i = 1; i < mMenuItemList.size(); ++i){
            mMenuTitle.add(mMenuItemList.get(i).getName());
        }
    }
    private int getDisplayWidth(){
        return activity.getWindowManager().getDefaultDisplay().getWidth() / 100 * 38;
    }
}
