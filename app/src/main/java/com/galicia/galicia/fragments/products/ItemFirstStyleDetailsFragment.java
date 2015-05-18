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
import com.galicia.galicia.untils.BitmapCreator;
import com.galicia.galicia.models.ItemSerializable;

public class ItemFirstStyleDetailsFragment extends Fragment {

    private FragmentActivity mCallingActivity;
    private ItemSerializable mStartItem;

    private ImageView mItemImage;
    private TextView mItemName;

    public ItemFirstStyleDetailsFragment() {
    }

    public static ItemFirstStyleDetailsFragment newInstance(final ItemSerializable _item) {
        ItemFirstStyleDetailsFragment fragment = new ItemFirstStyleDetailsFragment();
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
             mStartItem = (ItemSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_style_item, container, false);

        mItemName   = (TextView) view.findViewById(R.id.tvItemTitle_first_SI);
        mItemImage  = (ImageView) view.findViewById(R.id.ivItemImage_first_SI);

        makeData();
        return view;
    }

    private void makeData() {
        mItemName.setText(mStartItem.getItem().getName());
        mItemImage.setImageBitmap(BitmapCreator.getBitmap(mStartItem.getItem().getIcon()));
    }

}
