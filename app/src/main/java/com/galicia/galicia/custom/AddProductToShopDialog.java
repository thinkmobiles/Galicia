package com.galicia.galicia.custom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.SpinnerPurchaseAdapter;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.ItemSerializable;
import com.galicia.galicia.orm_database.DBManager;
import com.galicia.galicia.orm_database.Shop;
import com.galicia.galicia.untils.DataBase.ItemDAO;
import com.galicia.galicia.untils.DataBase.ShopDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasia on 26.05.2015.
 */
public class AddProductToShopDialog extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private MainActivity mCallingActivity;
    private int selected;

    private List<Shop> shopList, subList;
    private LinearLayout spinnerLayout;
    private Spinner spinner;
    private SpinnerPurchaseAdapter spinnerPurchaseAdapter;
    private TextView tvAccept, tvCancel;
    private EditText shopName;
    private Item mCurrentItem;
    private FrameLayout flTop, flBottom, flBackground;
    private EventListener mListener;
    private List<Product> mProductList;
    private List<Item> mThirdList;

    public static AddProductToShopDialog newInstance(final ItemSerializable _item) {
        final AddProductToShopDialog fragment = new AddProductToShopDialog();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ITEM_SERIAZ, _item);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void show(MainActivity _mActivity){
        FragmentReplacer.addFragment(_mActivity, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ)).getItem();
            getArguments().remove(Constants.ITEM_SERIAZ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflaterView = inflater.inflate(R.layout.product_to_shop_dialog, container, false);
        findViews(inflaterView);
        setListeners();
        addProduct();
        mCallingActivity.setEnableMenu(false);
        return inflaterView;
    }

    private void initSpinner(){
        spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(mCallingActivity, subList);
        spinner.setAdapter(spinnerPurchaseAdapter);
    }

    private void findViews(final View _view) {
        spinnerLayout   = (LinearLayout) _view.findViewById(R.id.llSpinner_PSD);
        spinner         = (Spinner) _view.findViewById(R.id.spinner_PSD);
        tvCancel        = (TextView) _view.findViewById(R.id.tvCancel_PSD);
        tvAccept        = (TextView) _view.findViewById(R.id.tvAccept_PSD);
        shopName        = (EditText) _view.findViewById(R.id.etNewShop_PSD);
        flTop           = (FrameLayout) _view.findViewById(R.id.flTop_PSD);
        flBottom        = (FrameLayout) _view.findViewById(R.id.flBottom_PSD);
        flBackground    = (FrameLayout) _view.findViewById(R.id.flBackground_PSD);
    }

    private void setListeners() {
        spinner.setOnItemSelectedListener(this);
        tvCancel.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        flBottom.setOnClickListener(this);
        flTop.setOnClickListener(this);
        flBackground.setOnClickListener(this);
        makeDownloadListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancel_PSD:
                if (shopName.getVisibility() == View.VISIBLE) {
                    spinnerLayout.setVisibility(View.VISIBLE);
                    shopName.setVisibility(View.GONE);
                } else {
                    FragmentReplacer.popSupBackStack(getActivity());
                }
                break;
            case R.id.tvAccept_PSD:
                onClickPositiveButton();

                break;
            case R.id.flTop_PSD:
            case R.id.flBottom_PSD:
                FragmentReplacer.popSupBackStack(getActivity());
                break;
        }
    }

    private void onClickPositiveButton(){
        if (shopName.getVisibility() == View.GONE) {
            if (subList.get(selected).getId() == null) {
                spinnerLayout.setVisibility(View.INVISIBLE);
                shopName.setVisibility(View.VISIBLE);
            } else {
                shopList.get(selected).getId();
                ApiManager.getThirdLevel(mListener, mCurrentItem);
                FragmentReplacer.popSupBackStack(getActivity());
                Toast.makeText(mCallingActivity, "Item add to shop_id= " + String.valueOf(subList.get(selected).getId()), Toast.LENGTH_SHORT).show();

            }
        } else if (!shopName.getText().toString().isEmpty()) {
            DBManager.addShop(shopName.getText().toString());
            spinnerLayout.setVisibility(View.VISIBLE);
            shopName.setVisibility(View.GONE);
            addProduct();
//            FragmentReplacer.popSupBackStack(getActivity());
        } else {
            Toast.makeText(mCallingActivity, "enter shop", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeDownloadListener() {
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThirdList = ApiManager.getThirdList();
                        getProduct();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList.add(ApiManager.getProductsList().get(0));
                        if (mProductList.size() == mThirdList.size()){
                            DBManager.addItem(
                                    mCurrentItem.getPdf(),
                                    subList.get(selected),
                                    mCurrentItem.getName(),
                                    mProductList
                            );
                        }

                }
            }
        };
    }

    private void getProduct() {
        if (mProductList == null) {
            mProductList = new ArrayList<>();
            for (Item item : mThirdList) {
                ApiManager.getProducts(mListener, item);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addProduct(){
        shopList = new ArrayList<>();
        subList = new ArrayList<>();
        shopList = DBManager.getShops();
        if (!shopList.isEmpty()) {
            Shop lastElement = shopList.get(shopList.size() - 1);
            shopList.add(0, lastElement);
            subList = shopList.subList(0, shopList.size() - 1);
        }
        subList.add(new Shop("Create new shop"));
        initSpinner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallingActivity.setEnableMenu(true);
    }
}
