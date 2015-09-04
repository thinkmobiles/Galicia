package com.cristaliza.nacional.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cristaliza.nacional.MainActivity;
import com.cristaliza.nacional.R;
import com.cristaliza.nacional.adapters.ShopCartAdapter;
import com.cristaliza.nacional.global.FragmentReplacer;
import com.cristaliza.nacional.orm_database.DBManager;
import com.cristaliza.nacional.orm_database.Shop;

import java.util.List;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class ShopCartFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ShopCartAdapter shopCartAdapter;
    private List<Shop> data;
    private ListView purchaseList;
    private ImageView deleteItems,ivGoBack;
    private MainActivity callActivity;
    private static ShopCartFragment fragment;

    public static ShopCartFragment newInstance() {
        if (fragment == null) {
            fragment = new ShopCartFragment();
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = DBManager.getShops();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart_shopping, container, false);

        findUI(rootView);
        setClickListener();

        shopCartAdapter = new ShopCartAdapter(callActivity, data, this);
        purchaseList.setAdapter(shopCartAdapter);
        return rootView;
    }

    public void findUI(View view) {
        deleteItems = (ImageView) view.findViewById(R.id.iv_deleteAll_FS);
        purchaseList = (ListView) view.findViewById(R.id.lv_list_Shopping_FS);
        ivGoBack = (ImageView) view.findViewById(R.id.iv_back_FPU);
        view.findViewById(R.id.tw_guardar_button_FS).setVisibility(View.INVISIBLE);
        callActivity.setEnableMenu(true);
        callActivity.setTitle(callActivity.getString(R.string.title_envio));
        callActivity.setBackground();
    }

    public void setClickListener() {
        deleteItems.setOnClickListener(this);
        purchaseList.setOnItemClickListener(this);
        ivGoBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_deleteAll_FS:
                deleteAllShop();
                break;

            case R.id.iv_back_FPU:
                super.getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentReplacer.replaceFragmentWithStack(callActivity,
                ItemCartFragment.newInstance(data.get(position).getId(), data.get(position).getName()));
    }

    public void updateDate() {
        data = DBManager.getShops();
    }

    public void deleteAllShop() {
        if (!data.isEmpty()) {
            DBManager.deleteAllShop();
            shopCartAdapter.updateList(DBManager.getShops());
            Toast.makeText(getActivity(), R.string.delete_all_shop, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.empty_cart, Toast.LENGTH_SHORT).show();
        }
    }
}
