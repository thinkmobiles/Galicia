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
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.ItemCartAdapter;
import com.galicia.galicia.custom.CustomDialog;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.orm_database.DBManager;
import com.galicia.galicia.orm_database.DBProduct;
import com.galicia.galicia.untils.DataBase.ItemDAO;

import java.util.List;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class ItemCartFragment extends Fragment implements View.OnClickListener {
    private ItemCartAdapter itemCartAdapter;
    private List<DBProduct> data;
    private ListView purchaseList;
    private ImageView deleteItems, ivGoBack;
//    private ItemDAO itemDAO;
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
//        itemDAO = new ItemDAO(callActivity);
//        data = itemDAO.getItems(shopId);
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

    }

    private void setListeners(){
        deleteItems.setOnClickListener(this);
        ivGoBack.setOnClickListener(this);
    }

    private void initUI(){
        callActivity.setEnableMenu(true);
        callActivity.setTitle(shopName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_deleteAll_FS:
                startDeleteDialog();
                break;
            case R.id.iv_back_FPU:
                super.getActivity().onBackPressed();
                break;
        }
    }

    public void updateDate() {
        data.clear();
//        data.addAll(itemDAO.getItems(shopId));
        data.addAll(DBManager.getProducts(shopId));
        itemCartAdapter.updateList(data);
    }

    private void startDeleteDialog(){
        if (DBManager.getProducts(shopId).size() == 0)
            return;
        final CustomDialog.Builder builder = new CustomDialog.Builder()
                .setMessage(getActivity().getString(R.string.delete_all_products))
                .setPositiveButton(getActivity().getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAllItems();
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.cancel), null);
        builder.createDialog().show(getActivity());
    }

    public void deleteAllItems() {
        if (!data.isEmpty()) {
//            itemDAO.deleteAll(shopId);
            DBManager.deleteAllItems(shopId);
            updateDate();
            Toast.makeText(getActivity(), R.string.delete_all_item, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.empty_cart, Toast.LENGTH_SHORT).show();
        }
    }
}
