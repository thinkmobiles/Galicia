package com.galicia.galicia;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.fragments.StartMenu;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.untils.SlidingMenuManager;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView menuBtn;
    private LinearLayout mBackgroundLayout;
    private TextView mTitle;
    private SlidingMenuManager manager;
    private boolean doubleBackToExitPressedOnce;
    private boolean clickable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container);
        findUI();
        ApiManager.init(this);
        ApiManager.setOfflineMode();
        initMenu();
        openStartMenu();
    }


    private void findUI() {
        menuBtn = (ImageView) findViewById(R.id.ivMenu);
        mTitle  = (TextView) findViewById(R.id.tvMenuTitle);
        mBackgroundLayout = (LinearLayout) findViewById(R.id.llAppContainer);
        menuBtn.setOnClickListener(this);
    }

    private void initMenu() {
        manager = new SlidingMenuManager();
        manager.initMenu(this);
    }

    public void setEnableMenu(boolean _status){
        manager.enableMenu(_status);
        clickable = _status;
    }

    @Override
    public void onClick(View v) {
        if(clickable)
            manager.show();
    }

    public void setTitle(String _title) {
        mTitle.setText(_title);
    }

    public void setBackground(final String _path) {
        if (_path == null)
            setBackground();
        else
            mBackgroundLayout.setBackgroundDrawable(Drawable.createFromPath(ApiManager.getPath() + _path));
    }

    public void setBackground() {
        mBackgroundLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
    }

    public void openStartMenu(){
        FragmentReplacer.replaceFragmentWithStack(this, new StartMenu());
    }

    public void onBackPressed() {
        if (manager.isShowing()) {
            manager.toggle();
            return;
        }

        if (FragmentReplacer.getSupBackStackEntryCount(this) == 1) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                MainActivity.this.finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.click_back), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            goBack();
        }
    }

    private void goBack() {
        if (FragmentReplacer.getSupBackStackEntryCount(this) != 0) {
            FragmentReplacer.popSupBackStack(this);
        }
    }
}
