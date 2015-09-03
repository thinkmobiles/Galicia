package com.galicia.galicia.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;

import java.util.ArrayList;

public class SlidePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ViewPager mPager;


    public SlidePagerAdapter(ViewPager _pager, final ArrayList<Fragment> fragmentArrayList, final FragmentManager fm) {
        super(fm);
        this.mFragments = fragmentArrayList;
        this.mPager = _pager;
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
