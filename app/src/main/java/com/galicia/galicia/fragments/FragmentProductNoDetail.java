package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.ItemSerializable;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class FragmentProductNoDetail extends Fragment {

    private ImageView mCompanyLogo,mProductImage;
    private ImageView mAddProductBtn;
    private TextView mProductInfo;
    private MainActivity mCallingActivity;
    private ItemSerializable mCurentItem;


    public FragmentProductNoDetail() {
    }

    public static FragmentProductNoDetail newInstance(final ItemSerializable _item) {
        FragmentProductNoDetail fragment = new FragmentProductNoDetail();
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
        View view = inflater.inflate(R.layout.fragment_product_no_detail, container, false);

        mCompanyLogo = (ImageView) view.findViewById(R.id.ivCompanyLogo);
        mProductImage = (ImageView) view.findViewById(R.id.ivProductImage);

        mAddProductBtn = (ImageView) view.findViewById(R.id.ivAddProduct);
        mProductInfo =(TextView) view.findViewById(R.id.tvProductDescription);
        setImage();
        return view;
    }

    public void setImage(){
        mProductImage.setImageResource(R.drawable.bg_vinos);
    }
}
