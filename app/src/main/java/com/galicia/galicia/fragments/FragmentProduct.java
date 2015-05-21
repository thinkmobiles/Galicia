package com.galicia.galicia.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.HorizontalPhotoProductAdapter;
import com.galicia.galicia.adapters.ProductVideoAdapter;
import com.galicia.galicia.adapters.SpinnerPurchaseAdapter;
import com.galicia.galicia.custom.HorizontalListView;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.ItemSerializable;
import com.galicia.galicia.models.Shop;
import com.galicia.galicia.untils.BitmapCreator;
import com.galicia.galicia.untils.DataBase.ItemDAO;

import com.galicia.galicia.untils.DataBase.ShopDAO;

import java.util.ArrayList;
import java.util.List;

public class FragmentProduct extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MainActivity mCallingActivity;
    private Item mCurrentItem;
    private ImageView ivProductPhoto, ivCompanyLogo, ivAddProduct, ivFicha;
     private HorizontalListView hlvAllProduct;
    private ListView lvProductVideo;
    private TextView tvProductPhotoTitle;
    private WebView wvProductDescription;
    private EventListener mListener;
    private ArrayList<Product> mProductList;
    private List<Item> mThirdList;
    private RelativeLayout rlProductPhotoContainer;
    private LinearLayout llCompanyLogo, llDetail, llMoreDetail;
    private ArrayList<Item> items;
    private int selected;
    private ShopDAO shopDAO;
    private ItemDAO itemDAO;
    private List<Shop> shopList;
    private LinearLayout spinerLayout;
    private Spinner spinner;
    private SpinnerPurchaseAdapter spinnerPurchaseAdapter;
    private TextView positivButton, negativButton;
    private EditText shopName;
    private AlertDialog alertDialog;
    private ScrollView svDescriptionContainer;

    public static FragmentProduct newInstance(final ItemSerializable _item) {
        final FragmentProduct fragment = new FragmentProduct();
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
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ)).getItem();
            getArguments().remove(Constants.ITEM_SERIAZ);
        }
        shopDAO = new ShopDAO(activity);
        itemDAO = new ItemDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_product, _container, false);
        findUI(view);
        setListener();
        makeData();
        ApiManager.getThirdLevel(mListener, mCurrentItem);
        verifyFichaCata();
        return view;
    }

    private void findUI(final View _view){
        ivAddProduct            = (ImageView) _view.findViewById(R.id.ivAddProduct_FPU);
        ivFicha                 = (ImageView) _view.findViewById(R.id.ivFichaCata_FPU);
        wvProductDescription    = (WebView) _view.findViewById(R.id.wvProductDescription_FPU);
        tvProductPhotoTitle     = (TextView) _view.findViewById(R.id.tvThirdProductTitle_FPU);
        hlvAllProduct           = (HorizontalListView) _view.findViewById(R.id.hlvAllProduct_FPU);
        lvProductVideo          = (ListView) _view.findViewById(R.id.lvProductVideo_FPU);
        ivProductPhoto          = (ImageView) _view.findViewById(R.id.ivProductPhoto_FPU);
        rlProductPhotoContainer = (RelativeLayout) _view.findViewById(R.id.rlProductPhotoContainer_FPU);
        ivCompanyLogo           = (ImageView) _view.findViewById(R.id.ivCompanyLogo_FPU);
        llCompanyLogo           = (LinearLayout)_view.findViewById(R.id.llCompanyLogo_FPU);
        llDetail                = (LinearLayout) _view.findViewById(R.id.llDetailContainer_FPU);
        llMoreDetail            = (LinearLayout) _view.findViewById(R.id.llMoreDetailContainer_FPU);

        svDescriptionContainer = (ScrollView) _view.findViewById(R.id.svDescriptionContainer_FPU);
    }

    private void setListener(){
        ivAddProduct.setOnClickListener(this);
        ivFicha.setOnClickListener(this);
        llMoreDetail.setOnClickListener(this);
        makeDownloadListener();
    }

    private void makeDownloadListener(){
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThirdList = ApiManager.getThirdList();
                        getProduct();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList.add(ApiManager.getProductsList().get(0));
                            initProductDetail();
                }
            }
        };
    }

    private void getProduct(){
        if (mProductList == null) {
            mProductList = new ArrayList<Product>();
            for (Item item : mThirdList){
                ApiManager.getProducts(mListener, item);
            }
        }
        else initProductDetail();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivAddProduct_FPU:
                addProduct();
                break;
            case R.id.ivFichaCata_FPU:
                FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FichaFragment.newInstance(mCurrentItem.getFichaCata()));
                break;
            case R.id.llMoreDetailContainer_FPU:
                startSlideFragment(0);
                break;
        }
    }

    private void verifyFichaCata(){
        if (mCurrentItem.getFichaCata() == null){
            ivFicha.setVisibility(View.GONE);
        }
    }

    private void initProductDetail(){
        if (mCurrentItem.getExtraVideos() != null && !mCurrentItem.getExtraVideos().isEmpty()){
            setProductVideoDetail();
        }
        else {
            setProductNoVideoDetail();
        }

    }

    private void setProductVideoDetail(){
        lvProductVideo.setVisibility(View.VISIBLE);
        llMoreDetail.setVisibility(View.VISIBLE);
        rlProductPhotoContainer.setVisibility(View.GONE);
        initVideoList();
        initHorizontalImageList();
    }

    private void setProductNoVideoDetail(){
        if (mThirdList == null)
            return;
        if (mThirdList.size() == 1) {
            setOneProductDetail();
        }
        else {
            llMoreDetail.setVisibility(View.GONE);
            calculateContainerSizeIfProductNoDetail();
            hlvAllProduct.setVisibility(View.VISIBLE);
            initHorizontalImageList();
        }
    }

    private void calculateContainerSizeIfProductNoDetail(){
        int companyLogoWidth = getDisplayWidth() / 8 *2;
        int productDetailWidth = getDisplayWidth() / 8 * 6;
        final LinearLayout.LayoutParams companyLogoParams = new LinearLayout.LayoutParams(companyLogoWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        final LinearLayout.LayoutParams productDetailParams = new LinearLayout.LayoutParams(productDetailWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        llCompanyLogo.setLayoutParams(companyLogoParams);
        llDetail.setLayoutParams(productDetailParams);
    }

    private void setOneProductDetail(){
        llMoreDetail.setVisibility(View.VISIBLE);
        hlvAllProduct.setVisibility(View.GONE);
        lvProductVideo.setVisibility(View.GONE);
        ivProductPhoto.setImageBitmap(BitmapCreator.getBitmap(mProductList.get(0).getImage()));
        tvProductPhotoTitle.setText(mThirdList.get(0).getName());
    }

    private void initHorizontalImageList(){
        if (mProductList.size() == 0){
            hlvAllProduct.setVisibility(View.GONE);
            return;
        }
        final HorizontalPhotoProductAdapter adapter = new HorizontalPhotoProductAdapter(mCallingActivity, mProductList);
        if (mCurrentItem.getDescription() == null || mCurrentItem.getDescription().equals("")) {
            adapter.setItemMargin(150);
        }
        hlvAllProduct.setAdapter(adapter);
        hlvAllProduct.setOnItemClickListener(this);
    }

    private void initVideoList(){
        lvProductVideo.setBackground(BitmapCreator.getDrawable(mCurrentItem.getExtraBackgroundImage()));
        final ProductVideoAdapter apter = new ProductVideoAdapter(mCallingActivity, mCurrentItem);
        lvProductVideo.setAdapter(apter);
        lvProductVideo.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().getClass().getName().equals(ProductVideoAdapter.class.getName()) ){
            startVideoPlayer(BitmapCreator.getAbsolutePath(mCurrentItem.getExtraVideos().get(position)));
        }


        if (parent.getAdapter().getClass().getName().equals(HorizontalPhotoProductAdapter.class.getName()) ){
            startSlideFragment(position);
        }

    }


    private void startVideoPlayer(String _path){
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_path));
        intent.setDataAndType(Uri.parse(_path), "video/*");
        startActivity(intent);
    }


    private void makeData() {
        ivCompanyLogo.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.getLogo()));
        mCallingActivity.setBackground(mCurrentItem.getBackgroundImage());
        ivCompanyLogo.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.getLogo()));
        makeWeb();

        if (mCurrentItem.getDescription() == null || mCurrentItem.getDescription().equals("")) {
            svDescriptionContainer.setVisibility(View.GONE);
        } else {
            wvProductDescription.loadDataWithBaseURL(
                    "",
                    mCurrentItem.getDescription(),
                    Constants.MIME_TYPE,
                    Constants.ENCODING,
                    ""
            );
        }
    }

    private void addProduct() {
        new GetShopTask().execute();
    }

    private int getDisplayWidth(){
        return mCallingActivity.getWindowManager().getDefaultDisplay().getWidth();
    }

    private void startSlideFragment(int _position){
        FragmentReplacer.replaceFragmentWithStack(
                mCallingActivity,
                FragmentSlide.newInstance(mProductList, _position));
    }

    public class GetShopTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            shopList = new ArrayList<>();
            shopList = shopDAO.getShops();
            shopList.add(new Shop(-1, "Create new shop"));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            createCustomDialog();
        }
    }

    public class GetItemTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for(Item item:itemDAO.getItems()){
                Log.d("AAA",item.getName());
            }
            return null;
        }

    }

    public void createCustomDialog(){
        final AlertDialog.Builder spinerDialog = new AlertDialog.Builder(mCallingActivity);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_spinner, null);

        findDialogUI(view);
        setDialogListener();

        spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(getActivity(), shopList);
        spinner.setAdapter(spinnerPurchaseAdapter);

        spinerDialog.setView(view);
        alertDialog = spinerDialog.create();
        alertDialog.show();

    }

    private void findDialogUI(View view){
        spinerLayout = (LinearLayout) view.findViewById(R.id.ll_spinner);
        spinner = (Spinner) view.findViewById(R.id.dialogSpinner);
        negativButton = (TextView) view.findViewById(R.id.tv_cancel_action_CD);
        positivButton = (TextView) view.findViewById(R.id.tv_accept_action_CD);
        shopName = (EditText) view.findViewById(R.id.et_new_Shop_CD);
    }
    private void setDialogListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                Log.d("QQQ", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        negativButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopName.getVisibility() == View.VISIBLE) {
                    spinerLayout.setVisibility(View.VISIBLE);
                    shopName.setVisibility(View.GONE);
                } else
                    alertDialog.dismiss();
            }
        });

        positivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopName.getVisibility() == View.GONE) {
                    if (shopList.get(selected).getId() == -1) {
                        spinerLayout.setVisibility(View.INVISIBLE);
                        shopName.setVisibility(View.VISIBLE);
                    } else {
                        shopList.get(selected).getId();
                        itemDAO.save(mCurentItem,shopList.get(selected).getId());
                        alertDialog.dismiss();
                        Toast.makeText(mCallingActivity,  "Item add to shop_id= " + String.valueOf(shopList.get(selected).getId()), Toast.LENGTH_SHORT).show();
                        new  GetItemTask().execute();
                    }
                } else if (!shopName.getText().toString().isEmpty()) {
                    shopDAO.save(new Shop(shopName.getText().toString()));
                    spinerLayout.setVisibility(View.VISIBLE);
                    shopName.setVisibility(View.GONE);
                    addProduct();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(mCallingActivity, R.string.enter_shop, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
