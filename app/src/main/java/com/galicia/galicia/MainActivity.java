package com.galicia.galicia;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.galicia.galicia.fragments.StartMenu;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.untils.SlidingMenuManager;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageButton menuBtn;
    private TextView mTitle;
    private SlidingMenuManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container);
        findUI();
//        initMenu();
        ApiManager.init(this);
        ApiManager.setOfflineMode();
        openStartMenu();
    }



    private void findUI() {
        menuBtn = (ImageButton) findViewById(R.id.ibMenu);
        mTitle  = (TextView) findViewById(R.id.title);
        menuBtn.setOnClickListener(this);
    }

    private void initMenu() {
        manager = new SlidingMenuManager();
        manager.initMenu(this);
    }

    @Override
    public void onClick(View v) {
        manager.show();
    }

    public void setTitle(String _title) {
        mTitle.setText(_title);
    }

    public void setBackround() {

    }

    private void openStartMenu(){
        FragmentReplacer.replaceTopNavigationFragment(this, new StartMenu());
    }


}
