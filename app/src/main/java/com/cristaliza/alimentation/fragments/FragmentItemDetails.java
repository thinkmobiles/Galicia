package com.cristaliza.alimentation.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.alimentation.MainActivity;
import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.global.Constants;
import com.cristaliza.alimentation.models.ProductSerializable;
import com.cristaliza.alimentation.untils.BitmapCreator;

public class FragmentItemDetails extends Fragment {
    private MainActivity mCallingActivity;
    private ProductSerializable mSProduct;
    private ImageView mItemImage;
    private TextView mItemName, mItemEan;
    private WebView[] webViews;

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

    private void findViews(View _view) {
        mItemName = (TextView) _view.findViewById(R.id.tvItemTitle_CS);
        mItemEan = (TextView) _view.findViewById(R.id.tvEAN_CS);
        mItemImage = (ImageView) _view.findViewById(R.id.ivItemImage_CS);
        webViews = new WebView[]{
                (WebView) _view.findViewById(R.id.tvItemFormatOne_CS),
                (WebView) _view.findViewById(R.id.tvItemFormatTwo_CS),
                (WebView) _view.findViewById(R.id.tvItemFormatThree_CS),
                (WebView) _view.findViewById(R.id.tvItemFormatFour_CS)
        };
        mCallingActivity.setEnableMenu(true);
    }

    private void makeData() {
        mItemName.setText(mSProduct.getProduct().getName());
        if (mSProduct.getProduct().getEAN() != null)
            mItemEan.setText("EAN " + mSProduct.getProduct().getEAN());
        mItemImage.setImageBitmap(BitmapCreator.getBitmap(mSProduct.getProduct().getImage()));
        if (mSProduct.getProduct().getPackaging() != null)
            for(int i = 0; i < mSProduct.getProduct().getPackaging().size(); ++i){
                showInfo(webViews[i], i);
            }
    }

    private void showInfo(WebView web, int indexText){
        web.loadDataWithBaseURL(
                "",
                mSProduct.getProduct().getPackaging().get(indexText),
                Constants.MIME_TYPE,
                Constants.ENCODING,
                ""
        );
        web.setVisibility(View.VISIBLE);
    }


}
