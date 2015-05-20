package com.galicia.galicia.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.SpinnerPurchaseAdapter;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.global.ItemsPurchaseList;
import com.galicia.galicia.models.ItemSerializable;

import java.util.ArrayList;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class FragmentProductNoDetail extends Fragment implements View.OnClickListener {

    private ImageView mCompanyLogo, mProductImage;
    private ImageView mAddProductBtn;
    private TextView mProductInfo;
    private MainActivity mCallingActivity;
    private ItemSerializable mCurentItem;
    private ArrayList<Item> items;
    private int selected;


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
        items = ItemsPurchaseList.getInstance(getActivity()).getItems();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_no_detail, container, false);

        mCompanyLogo = (ImageView) view.findViewById(R.id.ivCompanyLogo);
        mProductImage = (ImageView) view.findViewById(R.id.ivProductImage);

        mAddProductBtn = (ImageView) view.findViewById(R.id.ivProductPreview);
        mProductInfo = (TextView) view.findViewById(R.id.tvProductDescription);
        setImage();
        setClickListener();
        return view;
    }

    private void setClickListener() {
        mAddProductBtn.setOnClickListener(this);
    }

    public void setImage() {
        mProductImage.setImageResource(R.drawable.bg_vinos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivProductPreview:
                showDialog();
        }

    }

    private void showDialog() {

//        final AlertDialog.Builder spinerDialog = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.custom_dialog_spinner, null);
//
//        Spinner spinner = (Spinner) view.findViewById(R.id.dialogSpinner);
//
//        if(items.isEmpty())
//            spinner.setVisibility(View.GONE);
//
//        TextView negativButton = (TextView) view.findViewById(R.id.tv_cancel_action_CD);
//        TextView positivButton = (TextView) view.findViewById(R.id.tv_accept_action_CD);
//
//        SpinnerPurchaseAdapter spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(getActivity(), items);
//
//        spinner.setAdapter(spinnerPurchaseAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selected = position;
//                Log.d("QQQ", String.valueOf(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinerDialog.setView(view);
//        final AlertDialog alertDialog = spinerDialog.create();
//        alertDialog.show();
//
//        negativButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        positivButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (items.isEmpty()) {
//                    ItemsPurchaseList.getInstance(getActivity()).addItem(mCurentItem.getItem());
//                    alertDialog.dismiss();
//                } else {
//                    ItemsPurchaseList.getInstance(getActivity()).moveItem(selected, mCurentItem.getItem());
//                    alertDialog.dismiss();
//                }
//
//            }
//        });

    }
}
