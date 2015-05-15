package com.galicia.galicia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.galicia.galicia.R;
import com.galicia.galicia.adapters.SlidePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class FragmentSlide extends Fragment implements View.OnClickListener {

    private ImageView mBtnNext, mBtnPrev;
    private ViewPager mSlidePager;

    private SlidePagerAdapter mSlidePagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_product, container, false);

        mBtnNext = (ImageView) view.findViewById(R.id.ivNext);
        mBtnPrev = (ImageView) view.findViewById(R.id.ivPrev);

        mSlidePager = (ViewPager) view.findViewById(R.id.vpSlider);
        initPager();
        setListeners();

        return view;
    }

    private void initPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentProductNoDetail());
        fragments.add(new FragmentProductNoDetail());
        fragments.add(new FragmentProductNoDetail());
        mSlidePagerAdapter = new SlidePagerAdapter(mSlidePager, fragments, getFragmentManager());
        mSlidePager.setAdapter(mSlidePagerAdapter);
    }

    private void setListeners(){
        mBtnNext.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivNext:
                mSlidePagerAdapter.refreshPager(true);
                break;
            case R.id.ivPrev:
                mSlidePagerAdapter.refreshPager(false);
                break;
        }

    }
}
