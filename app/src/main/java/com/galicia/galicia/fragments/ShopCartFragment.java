package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.ShopCartAdapter;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.Shop;
import com.galicia.galicia.untils.DataBase.ShopDAO;

import java.util.List;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class ShopCartFragment extends Fragment implements View.OnClickListener {
    private ShopCartAdapter shopCartAdapter;
    private List<Shop> data;
    private ListView purchaseList;
    private ImageView deleteItems;
    private ShopDAO shopDAO;
    private Button guardarButton;
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

        shopDAO = new ShopDAO(activity);
        data = shopDAO.getShops();
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

        shopCartAdapter = new ShopCartAdapter(callActivity, data);
        purchaseList.setAdapter(shopCartAdapter);
        return rootView;
    }

    public void findUI(View view) {
        deleteItems = (ImageView) view.findViewById(R.id.iv_deleteAll_FS);
        purchaseList = (ListView) view.findViewById(R.id.lv_list_Shopping_FS);
        guardarButton = (Button) view.findViewById(R.id.tw_guardar_button_FS);
        guardarButton.setVisibility(View.INVISIBLE);

        callActivity.setEnableMenu(true);
        callActivity.setTitle(callActivity.getString(R.string.title_envio));
    }

    public void setClickListener() {
        deleteItems.setOnClickListener(this);
        purchaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentReplacer.replaceFragmentWithStack(callActivity,
                        ItemCartFragment.newInstance(String.valueOf(data.get(position).getId()), data.get(position).getName()));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_deleteAll_FS:
                if (!data.isEmpty()) {
                    shopDAO.deleteAll();
                    updateDate();
                    Toast.makeText(getActivity(), R.string.delete_all_shop, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.empty_cart, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void updateDate() {
        data.clear();
        data.addAll(shopDAO.getShops());
        shopCartAdapter.notifyDataSetChanged();
    }

}
