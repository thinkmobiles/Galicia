package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.ProductSerializable;
import com.galicia.galicia.untils.BitmapCreator;

public class FragmentItemDetails extends Fragment {

    private MainActivity mCallingActivity;
    private ProductSerializable mSProduct;

    private ImageView mItemImage;
    private TextView mItemName, mPackage1, mPackage2, mPackage3, mPackage4;

    public FragmentItemDetails() {
    }

    public static FragmentItemDetails newInstance(final ProductSerializable _item) {
        FragmentItemDetails fragment = new FragmentItemDetails();
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
            mSProduct = (ProductSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ);
            getArguments().remove(Constants.ITEM_SERIAZ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_style_item, container, false);

        mItemName           = (TextView) view.findViewById(R.id.tvItemTitle_CS);
        mItemImage          = (ImageView) view.findViewById(R.id.ivItemImage_CS);
        mPackage1           = (TextView) view.findViewById(R.id.tvItemFormatOne_CS);
        mPackage2           = (TextView) view.findViewById(R.id.tvItemFormatTwo_CS);
        mPackage3           = (TextView) view.findViewById(R.id.tvItemFormatThree_CS);
        mPackage4           = (TextView) view.findViewById(R.id.tvItemFormatFour_CS);

        mCallingActivity.setEnableMenu(true);
        makeData();
        return view;
    }

    private void makeData() {
        mItemName.setText(mSProduct.getProduct().getName());
        mItemImage.setImageBitmap(BitmapCreator.getBitmap(mSProduct.getProduct().getImage()));

        if (mSProduct.getProduct().getPackaging() != null)
            switch (mSProduct.getProduct().getPackaging().size()){
                case 1:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage2.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(1)));
                    mPackage1.setVisibility(View.VISIBLE);
                    mPackage2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage2.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(1)).toString());
                    mPackage3.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(2)).toString());
                    mPackage1.setVisibility(View.VISIBLE);
                    mPackage2.setVisibility(View.VISIBLE);
                    mPackage3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage2.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(1)).toString());
                    mPackage3.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(2)).toString());
                    mPackage4.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(3)).toString());
                    mPackage1.setVisibility(View.VISIBLE);
                    mPackage2.setVisibility(View.VISIBLE);
                    mPackage3.setVisibility(View.VISIBLE);
                    mPackage4.setVisibility(View.VISIBLE);
                    break;
            }
    }



}
