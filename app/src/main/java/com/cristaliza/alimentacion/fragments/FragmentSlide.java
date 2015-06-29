package com.cristaliza.alimentacion.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.cristaliza.mvc.models.estrella.Product;
import com.cristaliza.alimentacion.MainActivity;
import com.cristaliza.alimentacion.R;
import com.cristaliza.alimentacion.adapters.SlidePagerAdapter;
import com.cristaliza.alimentacion.global.Constants;
import com.cristaliza.alimentacion.models.ProductSerializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentSlide extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {


    private List<Product> mProductList;
    private int mPosition;

    private ImageView mBtnNext, mBtnPrev;
    private ViewPager mSlidePager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private SlidePagerAdapter mSlidePagerAdapter;
    private MainActivity mCallingActivity;

    public FragmentSlide() {
    }

    public static FragmentSlide newInstance(final ArrayList<Product> _productList, int _position) {
        FragmentSlide fragment = new FragmentSlide();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PRODUCT_LIST, _productList);
        bundle.putInt(Constants.POSITION, _position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mProductList = (List<Product>) getArguments().getSerializable(Constants.PRODUCT_LIST);
            mPosition = getArguments().getInt(Constants.POSITION);
            getArguments().remove(Constants.PRODUCT_LIST);
            getArguments().remove(Constants.POSITION);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_product, container, false);

        mBtnNext = (ImageView) view.findViewById(R.id.ivNext);
        mBtnPrev = (ImageView) view.findViewById(R.id.ivPrev);

        mCallingActivity.setEnableMenu(true);
        mSlidePager = (ViewPager) view.findViewById(R.id.vpSlider);
        makeList();
        initFragmentsList();
        setListeners();

        return view;
    }

    private void setListeners(){
        mBtnNext.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivNext:
                mSlidePager.setCurrentItem(mSlidePager.getCurrentItem()+1, true);
                break;
            case R.id.ivPrev:
                mSlidePager.setCurrentItem(mSlidePager.getCurrentItem()-1, true);
                break;
        }

    }

    private void initFragmentsList() {

        mSlidePagerAdapter = new SlidePagerAdapter(mSlidePager, fragments, getChildFragmentManager());
        mSlidePager.setAdapter(mSlidePagerAdapter);
        mSlidePager.setCurrentItem(mPosition);
        mSlidePager.setOnPageChangeListener(this);
        onPageChange(mPosition);
    }

    private void makeList() {
        for (Product product : mProductList){
            ProductSerializable mSproduct = new ProductSerializable();
            mSproduct.setProduct(product);
            fragments.add(FragmentItemDetails.newInstance(mSproduct));
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int _position) {
        mPosition = _position;
        onPageChange(_position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void onPageChange(int _position){
        if (mProductList.size() == 1){
            mBtnNext.setVisibility(View.GONE);
            mBtnPrev.setVisibility(View.GONE);
            return;
        }
        if (_position == mProductList.size() - 1) {
            mBtnNext.setVisibility(View.GONE);
            mBtnPrev.setVisibility(View.VISIBLE);
            return;
        }
        if (_position == 0) {
            mBtnPrev.setVisibility(View.GONE);
            mBtnNext.setVisibility(View.VISIBLE);
            return;
        }

        mBtnPrev.setVisibility(View.VISIBLE);
        mBtnNext.setVisibility(View.VISIBLE);
    }
}