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
import android.webkit.WebView;
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

    private ImageView mItemImage, mClose;
    private TextView mItemName, mItemEan;
    private WebView mPackage1, mPackage2, mPackage3, mPackage4;

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

        findViews(view);
        makeData();
        return view;
    }

    private void findViews(View _view){
        mItemName           = (TextView) _view.findViewById(R.id.tvItemTitle_CS);
        mItemEan            = (TextView) _view.findViewById(R.id.tvEAN_CS);
        mPackage1           = (WebView) _view.findViewById(R.id.tvItemFormatOne_CS);
        mPackage2           = (WebView) _view.findViewById(R.id.tvItemFormatTwo_CS);
        mPackage3           = (WebView) _view.findViewById(R.id.tvItemFormatThree_CS);
        mPackage4           = (WebView) _view.findViewById(R.id.tvItemFormatFour_CS);
        mItemImage          = (ImageView) _view.findViewById(R.id.ivItemImage_CS);

        mCallingActivity.setEnableMenu(true);
    }

    private void makeData() {
        mItemName.setText(mSProduct.getProduct().getName());
        mItemEan.setText(mSProduct.getProduct().getEAN());
        mItemImage.setImageBitmap(BitmapCreator.getBitmap(mSProduct.getProduct().getImage()));

        if (mSProduct.getProduct().getPackaging() != null)
            switch (mSProduct.getProduct().getPackaging().size()){
                case 1:
                    mPackage1.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(0),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mPackage1.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(0),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage2.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(1),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage1.setVisibility(View.VISIBLE);
                    mPackage2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mPackage1.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(0),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage2.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(1),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage3.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(2),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage1.setVisibility(View.VISIBLE);
                    mPackage2.setVisibility(View.VISIBLE);
                    mPackage3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    mPackage1.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(0),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage2.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(1),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage3.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(2),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage4.loadDataWithBaseURL(
                            "",
                            mSProduct.getProduct().getPackaging().get(3),
                            Constants.MIME_TYPE,
                            Constants.ENCODING,
                            ""
                    );
                    mPackage1.setVisibility(View.VISIBLE);
                    mPackage2.setVisibility(View.VISIBLE);
                    mPackage3.setVisibility(View.VISIBLE);
                    mPackage4.setVisibility(View.VISIBLE);
                    break;
            }
    }



}
