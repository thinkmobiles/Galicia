package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.PurchaseCartAdapter;
import com.galicia.galicia.global.ItemsPurchaseList;

import java.util.ArrayList;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class PurchaseCartFragment extends Fragment implements View.OnClickListener {
    private PurchaseCartAdapter purchaseCartAdapter;
    private ArrayList<Item> data;
    private ListView purchaseList;
    private ImageView deleteItems;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        data = ItemsPurchaseList.getInstance(getActivity()).getItems();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart_shopping, container, false);

        findUI(rootView);
        setClickListener();

        purchaseCartAdapter = new PurchaseCartAdapter(getActivity().getApplicationContext(), data);
        purchaseList.setAdapter(purchaseCartAdapter);
        return rootView;
    }

    public void findUI(View view) {
        deleteItems = (ImageView) view.findViewById(R.id.iv_deleteAll_FS);
        purchaseList = (ListView) view.findViewById(R.id.lv_list_Shopping_FS);
    }

    public void setClickListener() {
        deleteItems.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_deleteAll_FS:
                if (!data.isEmpty()) {
                    ItemsPurchaseList.getInstance(getActivity()).clearItems();
                    purchaseCartAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), R.string.empty_cart, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
