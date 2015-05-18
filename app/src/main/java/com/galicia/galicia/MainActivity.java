package com.galicia.galicia;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.galicia.galicia.fragments.PurchaseCartFragment;
import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.fragments.StartMenu;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.untils.SlidingMenuManager;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton menuBtn;
    private TextView mTitle;
    private SlidingMenuManager manager;
    private ImageView logo;
    private Item mCurrentItem;
    private boolean doubleBackToExitPressedOnce;
    private boolean clickable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container);
        findUI();
        initMenu();
        ApiManager.init(this);
        ApiManager.setOfflineMode();
        openStartMenu();
    }



    private void findUI() {
        menuBtn = (ImageButton) findViewById(R.id.ibMenu);
        mTitle  = (TextView) findViewById(R.id.tvMenuTitle);
        mTitle  = (TextView) findViewById(R.id.title);
        logo = (ImageView) findViewById(R.id.tvLogoTitle);
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

    public void setBackround() {

    }

    public void openStartMenu(){
        FragmentReplacer.replaceFragmentWithStack(this, new StartMenu());
    }

    public Item getCurrentItem() {
        return mCurrentItem;
    }

    public void setCurrentItem(Item mCurrentItem) {
        this.mCurrentItem = mCurrentItem;
    }

    public void onBackPressed() {
        if (FragmentReplacer.getSupBackStackEntryCount(this) == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
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
