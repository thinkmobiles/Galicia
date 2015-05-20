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

    private FragmentActivity mCallingActivity;
    private ProductSerializable mSProduct;

    private ImageView mItemImage;
    private TextView mItemName, mPackage1, mPackage2, mPackage3, mPackage4;
    private LinearLayout mPackagesContainer;

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
        View view = inflater.inflate(R.layout.fragment_fifth_style_item, container, false);

        mItemName           = (TextView) view.findViewById(R.id.tvItemTitle_fifth_SI);
        mItemImage          = (ImageView) view.findViewById(R.id.ivItemImage_fifth_SI);
        mPackagesContainer  = (LinearLayout) view.findViewById(R.id.llPackages);
        mPackage1           = (TextView) view.findViewById(R.id.tvItemFormatOne);
        mPackage2           =  (TextView) view.findViewById(R.id.tvItemFormatTwo);
        mPackage3           =  (TextView) view.findViewById(R.id.tvItemFormatThree);
        mPackage4           =  (TextView) view.findViewById(R.id.tvItemFormatFour);


        makeData();
        return view;
    }

    private void makeData() {
        mItemName.setText(mSProduct.getProduct().getName());
        mItemImage.setImageBitmap(BitmapCreator.getBitmap(mSProduct.getProduct().getImage()));

        if (mSProduct.getProduct().getPackaging() == null)
            mPackagesContainer.setVisibility(View.GONE);
        else
            switch (mSProduct.getProduct().getPackaging().size()){

                case 0:
                    mPackagesContainer.setVisibility(View.GONE);
                    break;
                case 1:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    break;
                case 2:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage2.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(1)));
                    mPackage2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage2.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(1)).toString());
                    mPackage3.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(2)).toString());
                    mPackage2.setVisibility(View.VISIBLE);
                    mPackage3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    mPackage1.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(0)).toString());
                    mPackage2.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(1)).toString());
                    mPackage3.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(2)).toString());
                    mPackage4.setText(Html.fromHtml(mSProduct.getProduct().getPackaging().get(3)).toString());
                    mPackage2.setVisibility(View.VISIBLE);
                    mPackage3.setVisibility(View.VISIBLE);
                    mPackage4.setVisibility(View.VISIBLE);
                    break;
                default:
                    mPackagesContainer.setVisibility(View.GONE);
                    break;
            }
    }



}
