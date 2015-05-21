package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.ItemCartAdapter;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.global.ItemsPurchaseList;
import com.galicia.galicia.models.ItemSerializable;
import com.galicia.galicia.models.Shop;
import com.galicia.galicia.untils.DataBase.GaliciaDataBaseHelper;
import com.galicia.galicia.untils.DataBase.ItemDAO;

import java.util.List;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class ItemCartFragment extends Fragment implements View.OnClickListener {
    private ItemCartAdapter itemCartAdapter;
    private List<Item> data;
    private ListView purchaseList;
    private ImageView deleteItems;
    private ItemDAO itemDAO;
    private String shopId;
    private FragmentActivity callActivity;

    public static ItemCartFragment newInstance(final String shop_id) {
        final ItemCartFragment fragment = new ItemCartFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(Constants.ITEM_SHOP_ID, shop_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callActivity = (MainActivity) activity;

        if (getArguments() != null) {
            shopId = getArguments().getString(Constants.ITEM_SHOP_ID);
            getArguments().remove(Constants.ITEM_SHOP_ID);
        }

        itemDAO = new ItemDAO(activity);
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

        data = itemDAO.getItems(shopId);

        itemCartAdapter = new ItemCartAdapter(callActivity, data);
        purchaseList.setAdapter(itemCartAdapter);
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
                    itemDAO.deleteAll();
                    itemCartAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), R.string.empty_cart, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
