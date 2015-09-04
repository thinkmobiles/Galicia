package com.cristaliza.alimentation.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.alimentation.MainActivity;
import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.adapters.ItemCartAdapter;
import com.cristaliza.alimentation.custom.CustomDialog;
import com.cristaliza.alimentation.global.Constants;
import com.cristaliza.alimentation.orm_database.DBManager;
import com.cristaliza.alimentation.orm_database.DBProduct;
import com.cristaliza.alimentation.untils.PDFSender;

import java.util.List;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class ItemCartFragment extends Fragment implements View.OnClickListener {
    private ItemCartAdapter itemCartAdapter;
    private List<DBProduct> data;
    private ListView purchaseList;
    private ImageView deleteItems, ivGoBack;
    private Button btnEnviar;
    private String shopName;
    private long shopId;
    private MainActivity callActivity;

    public static ItemCartFragment newInstance(final Long shop_id, final String shop_name) {

        ItemCartFragment fragment = new ItemCartFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.ITEM_SHOP_ID, shop_id);
        bundle.putString(Constants.ITEM_SHOP_NAME, shop_name);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callActivity = (MainActivity) activity;
        if (getArguments() != null) {
            shopId = getArguments().getLong(Constants.ITEM_SHOP_ID);
            shopName = getArguments().getString(Constants.ITEM_SHOP_NAME);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = DBManager.getProducts(shopId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart_shopping, container, false);
        findUI(rootView);
        setListeners();
        initUI();
        itemCartAdapter = new ItemCartAdapter(callActivity, data, shopId);
        purchaseList.setAdapter(itemCartAdapter);
        return rootView;
    }

    public void findUI(View view) {
        deleteItems = (ImageView) view.findViewById(R.id.iv_deleteAll_FS);
        purchaseList = (ListView) view.findViewById(R.id.lv_list_Shopping_FS);
        ivGoBack = (ImageView) view.findViewById(R.id.iv_back_FPU);
        btnEnviar = (Button) view.findViewById(R.id.tw_guardar_button_FS);
        ((TextView) view.findViewById(R.id.tv_locales_label_FS)).setText(callActivity.getString(R.string.listado_shopping));
    }

    private void setListeners(){
        deleteItems.setOnClickListener(this);
        ivGoBack.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);
    }

    private void initUI(){
        callActivity.setEnableMenu(true);
        callActivity.setTitle(shopName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_deleteAll_FS:
                deleteAllItems();
                break;
            case R.id.iv_back_FPU:
                super.getActivity().onBackPressed();
                break;
            case R.id.tw_guardar_button_FS:
                PDFSender.sendShopPDFs(callActivity, shopId);
                break;
        }
    }

    public void updateDate() {
        data.clear();
        data.addAll(DBManager.getProducts(shopId));
        itemCartAdapter.updateList(data);
    }

    public void deleteAllItems() {
        if (!data.isEmpty()) {
            DBManager.deleteAllItems(shopId);
            updateDate();
            Toast.makeText(getActivity(), R.string.delete_all_item, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.empty_cart, Toast.LENGTH_SHORT).show();
        }
    }
}
