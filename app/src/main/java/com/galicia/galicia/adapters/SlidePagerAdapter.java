package com.galicia.galicia.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    public SlidePagerAdapter(final ArrayList<Fragment> fragmentArrayList, final FragmentManager fm) {
        super(fm);
        this.mFragments = fragmentArrayList;
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
