package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.SlidePagerAdapter;
import com.galicia.galicia.fragments.products.ItemFirstStyleDetailsFragment;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.ItemSerializable;
import com.galicia.galicia.models.ProductSerializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentSlide extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ItemSerializable mCurentItem;
    private EventListener mListener;
    private List<Item> mThridList;
    private List<Product> mProductList;

    private ImageView mBtnNext, mBtnPrev;
    private ViewPager mSlidePager;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private SlidePagerAdapter mSlidePagerAdapter;

    public FragmentSlide() {
    }

    public static FragmentSlide newInstance(final ItemSerializable _item) {
        FragmentSlide fragment = new FragmentSlide();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ITEM_SERIAZ, _item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mCurentItem = (ItemSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_product, container, false);

        mBtnNext = (ImageView) view.findViewById(R.id.ivNext);
        mBtnPrev = (ImageView) view.findViewById(R.id.ivPrev);

        mSlidePager = (ViewPager) view.findViewById(R.id.vpSlider);
//        initPager();
        makeLisner();
        ApiManager.getThirdLevel(mListener, mCurentItem.getItem());
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
//                mSlidePagerAdapter.refreshPager(true);
                mSlidePager.setCurrentItem(mSlidePager.getCurrentItem()+1, true);
                break;
            case R.id.ivPrev:
//                mSlidePagerAdapter.refreshPager(false);
                mSlidePager.setCurrentItem(mSlidePager.getCurrentItem()-1, true);
                break;
        }

    }

    private void makeLisner() {
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThridList = ApiManager.getThirdList();
//                        ApiManager.getProducts(mListener, mThridList.get(0));
                        initFragmentsList();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList = ApiManager.getProductsList();
                        makeList();
                }
            }
        };
    }

    private void initFragmentsList() {
        for (Item item : mThridList) {
            ApiManager.getProducts(mListener, item);
        }

        mSlidePagerAdapter = new SlidePagerAdapter(mSlidePager, fragments, getChildFragmentManager());
        mSlidePager.setAdapter(mSlidePagerAdapter);
    }

    private void makeList() {
        for (Product product : mProductList){
            ProductSerializable mSproduct = new ProductSerializable();
            mSproduct.setProduct(product);
            fragments.add(ItemFirstStyleDetailsFragment.newInstance(mSproduct));
        }

    }
}
