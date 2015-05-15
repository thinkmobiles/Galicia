package com.galicia.galicia.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;

import java.util.ArrayList;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ViewPager mPager;


    public SlidePagerAdapter(ViewPager _pager, final ArrayList<Fragment> fragmentArrayList, final FragmentManager fm) {
        super(fm);
        this.mFragments = fragmentArrayList;
        this.mPager = _pager;
    }

    public void refreshPager(final boolean goNext){
        int position = mPager.getCurrentItem();
        if(goNext){
            if(position<mFragments.size()-1)
                mPager.setCurrentItem(++position,true);
        } else {
            if(position > 0)
                mPager.setCurrentItem(--position,true);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
