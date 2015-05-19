package com.galicia.galicia.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.HorisontalPhotoProductAdapter;
import com.galicia.galicia.adapters.ProductVideoAdapter;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.models.ItemSerializable;
import com.galicia.galicia.untils.BitmapCreator;
import com.galicia.galicia.untils.HorizontalListView;

import java.util.List;

/**
 * Created by vasia on 18.05.2015.
 */
public class FragmentProductUniversal extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ItemSerializable mCurentItem;
    private ImageView ivAddProduct, ivProductPhoto, ivCompanyLogo, ivFichaPrduct;
    private HorizontalListView hlvAllProduct;
    private ListView lvProductVideo;
    private TextView tvProductDetail;
    private EventListener mListener;
    private List<Product> mProductList;
    private List<Item> mThridList;
    private RelativeLayout rlThirdProduct, rlMoreDetail, rldetail;

    public static FragmentProductUniversal newInstance(final ItemSerializable _item) {
        final FragmentProductUniversal fragment = new FragmentProductUniversal();
        final Bundle bundle = new Bundle();
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

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_product_more_detail_universal, _container, false);
        findUI(view);
        initLists();
        setListener();
        ApiManager.getThirdLevel(mListener, mCurentItem.getItem());
        initProductDetail();
        makeData();
        return view;
    }

    private void findUI(final View _view){
        ivAddProduct = (ImageView) _view.findViewById(R.id.ivAddProduct_FPU);
        ivFichaPrduct = (ImageView) _view.findViewById(R.id.ivFichaProduct_FPU);
        tvProductDetail = (TextView) _view.findViewById(R.id.tvProductDetail_FPU);
        hlvAllProduct = (HorizontalListView) _view.findViewById(R.id.hlvAllProduct_FPU);
        lvProductVideo = (ListView) _view.findViewById(R.id.lvProductVideo_FPU);
        ivProductPhoto = (ImageView) _view.findViewById(R.id.ivProductPhoto_FPU);
        rlThirdProduct = (RelativeLayout) _view.findViewById(R.id.rlProductThird_FPU);
        rlMoreDetail = (RelativeLayout) _view.findViewById(R.id.rlMoreDetailContainer_FPU);
        rldetail = (RelativeLayout) _view.findViewById(R.id.rlMoreDetailContainer_FPU);
        ivCompanyLogo = (ImageView) _view.findViewById(R.id.ivCompanyLogo_FPU);
    }

    private void initLists(){

    }

    private void setListener(){
        ivAddProduct.setOnClickListener(this);
        ivFichaPrduct.setOnClickListener(this);

        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThridList = ApiManager.getThirdList();
                        ApiManager.getProducts(mListener, mThridList.get(0));
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList = ApiManager.getProductsList();
                        initHorisontalImageList();
                }
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivAddProduct_FPU:
                addProduct();
                break;
            case R.id.ivFichaProduct_FPU:

                break;
        }
    }

    private void initProductDetail(){
        if (mCurentItem.getItem().getExtraVideos() != null && !mCurentItem.getItem().getExtraVideos().isEmpty()){
            setProductVideoDetail();

        }
        else {
            setProductNoVideoDetail();
        }

    }

    private void setProductVideoDetail(){
        lvProductVideo.setVisibility(View.VISIBLE);
        rlMoreDetail.setVisibility(View.VISIBLE);
        rlThirdProduct.setVisibility(View.GONE);
        calculateContainerSizeVideoDetail();
        initVideoList();


    }

    private void calculateContainerSizeVideoDetail(){
        int
    }

    private void setProductNoVideoDetail(){

        if (mThridList.size() == 1 && lvProductVideo.getVisibility() == View.GONE) {
            setOneThirdProductDetail();
        }
        else {
            rlMoreDetail.setVisibility(View.GONE);
            hlvAllProduct.setVisibility(View.VISIBLE);
            ApiManager.getProducts(mListener, mCurentItem.getItem());
        }
    }

    private void setOneThirdProductDetail(){
        hlvAllProduct.setVisibility(View.GONE);
        lvProductVideo.setVisibility(View.GONE);
        ivProductPhoto.setImageBitmap(BitmapCreator.getBitmap(mThridList.get(0).getIcon()));
    }

    private void initHorisontalImageList(){
        final HorisontalPhotoProductAdapter adapter = new HorisontalPhotoProductAdapter(mCallingActivity, mProductList);
        hlvAllProduct.setAdapter(adapter);
    }


    private void initVideoList(){
        final ProductVideoAdapter apter = new ProductVideoAdapter(mCallingActivity, mCurentItem.getItem());
        lvProductVideo.setAdapter(apter);
        lvProductVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    private void makeData(){
        ivCompanyLogo.setImageBitmap(BitmapCreator.getBitmap(mCurentItem.getItem().getLogo()));

    }

    private void addProduct(){

    }

    private int getDisplayWidth(){
        return mCallingActivity.getWindowManager().getDefaultDisplay().getWidth();
    }

}
