package com.galicia.galicia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.galicia.galicia.R;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class FragmentProductNoDetail extends Fragment {

    private ImageView mCompanyLogo,mProductImage;
    private ImageView mAddProductBtn;
    private TextView mProductInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_no_detail, container, false);

        mCompanyLogo = (ImageView) view.findViewById(R.id.ivCompanyLogo);
        mProductImage = (ImageView) view.findViewById(R.id.ivProductPreview);

        mAddProductBtn = (ImageView) view.findViewById(R.id.ivAddProduct);
        mProductInfo =(TextView) view.findViewById(R.id.tvProductDescription);
        return view;
    }
}
