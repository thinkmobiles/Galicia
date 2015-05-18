package com.galicia.galicia.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.ItemSerializable;

import java.util.List;

/**
 * Created by Bogdan on 10.05.2015.
 */
public class FragmentProductDetail extends Fragment {
    private ImageView mCompanyLogo, mProductPreview;
    private ImageView mAddProductBtn;
    private TextView mDiscription, mNameProduct;
    private EventListener mListener;
    private MainActivity mCallingActivity;
    private ItemSerializable mCurentItem;
    private List<Item> mThridList;
    private List<Product> mProductList;




    public FragmentProductDetail() {
    }

    public static FragmentProductDetail newInstance(final ItemSerializable _item) {
        FragmentProductDetail fragment = new FragmentProductDetail();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        mCompanyLogo = (ImageView) view.findViewById(R.id.ivCompanyLogo);
        mProductPreview = (ImageView) view.findViewById(R.id.ivProductPreview);

        mAddProductBtn = (ImageView) view.findViewById(R.id.ivAddProduct);
        mDiscription =(TextView) view.findViewById(R.id.tvProductDescription);
        mNameProduct = (TextView) view.findViewById(R.id.tvNameProductPrev);
        makeListener();
        ApiManager.getThirdLevel(mListener, mCurentItem.getItem());

        return view;
    }



    private void makeListener() {
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThridList = ApiManager.getThirdList();
                        makeData();
                        ApiManager.getProducts(mListener, mThridList.get(0));
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList = ApiManager.getProductsList();
                }
            }
        };
    }

    private void makeData() {
        mCompanyLogo.setImageBitmap(getBitmap(mCurentItem.getItem().getLogo()));
        mProductPreview.setImageBitmap(getBitmap(mCurentItem.getItem().getIcon()));
        mNameProduct.setText(mCurentItem.getItem().getName());
    }


    private Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

}
