package com.galicia.galicia.fragments.products;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.ProductSerializable;
import com.galicia.galicia.untils.BitmapCreator;

/**
 * Created by Bogdan on 16.05.2015.
 */
public class ItemSecondStyleDetailsFragment extends Fragment{

    private FragmentActivity mCallingActivity;
    private ProductSerializable productSerializable;

    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemFormat;

    public ItemSecondStyleDetailsFragment() {
    }

    public static ItemSecondStyleDetailsFragment newInstance(final ProductSerializable _item) {
        ItemSecondStyleDetailsFragment fragment = new ItemSecondStyleDetailsFragment();
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
            productSerializable = (ProductSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_style_item, container, false);

        mItemImage  = (ImageView) view.findViewById(R.id.ivItemImage_first_SI);
        mItemName   = (TextView) view.findViewById(R.id.tvItemTitle_first_SI);
        mItemFormat = (TextView) view.findViewById(R.id.tvItemFormat);

        makeData();
        return view;
    }

    private void makeData() {
        mItemName.setText(productSerializable.getProduct().getName());
        mItemImage.setImageBitmap(BitmapCreator.getBitmap(productSerializable.getProduct().getImage()));
//        mItemFormat.setText("Format");
//        mItemFormat.setText(Html.fromHtml());
    }

}
