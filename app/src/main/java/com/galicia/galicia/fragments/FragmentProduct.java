package com.galicia.galicia.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.galicia.galicia.PlayVideoActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.HorizontalPhotoProductAdapter;
import com.galicia.galicia.adapters.ProductVideoAdapter;
import com.galicia.galicia.custom.AddProductToShopDialog;
import com.galicia.galicia.custom.HorizontalListView;
import com.galicia.galicia.global.ApiManager;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.ItemSerializable;
import com.galicia.galicia.untils.BitmapCreator;

import java.util.ArrayList;
import java.util.List;

public class FragmentProduct extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MainActivity mCallingActivity;
    private Item mCurrentItem;
    private ImageView ivProductPhoto, ivCompanyLogo, ivAddProduct, ivFicha, ivGoBack, ivDownScroll;
    private HorizontalListView hlvAllProduct;
    private ListView lvProductVideo;
    private TextView tvProductPhotoTitle;
    private WebView wvProductDescription;
    private EventListener mListener;
    private ArrayList<Product> mProductList;
    private List<Item> mThirdList;
    private RelativeLayout rlProductPhotoContainer;
    private LinearLayout llCompanyLogo, llDetail, llMoreDetail, llVideo, llDeac;

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

    private void findUI(final View _view) {
        ivAddProduct            = (ImageView) _view.findViewById(R.id.ivAddProduct_FPU);
        ivFicha                 = (ImageView) _view.findViewById(R.id.ivFichaCata_FPU);
        ivGoBack                = (ImageView) _view.findViewById(R.id.iv_back_FPU);
        ivDownScroll            = (ImageView) _view.findViewById(R.id.iv_downScroll_FP);
        ivCompanyLogo           = (ImageView) _view.findViewById(R.id.ivCompanyLogo_FPU);
        ivProductPhoto          = (ImageView) _view.findViewById(R.id.ivProductPhoto_FPU);
        wvProductDescription    = (WebView) _view.findViewById(R.id.wvProductDescription_FPU);
        tvProductPhotoTitle     = (TextView) _view.findViewById(R.id.tvThirdProductTitle_FPU);
        hlvAllProduct           = (HorizontalListView) _view.findViewById(R.id.hlvAllProduct_FPU);
        lvProductVideo          = (ListView) _view.findViewById(R.id.lvProductVideo_FPU);
        rlProductPhotoContainer = (RelativeLayout) _view.findViewById(R.id.rlProductPhotoContainer_FPU);
        llVideo                 = (LinearLayout) _view.findViewById(R.id.ll_video_container_FP);
        llCompanyLogo           = (LinearLayout) _view.findViewById(R.id.llCompanyLogo_FPU);
        llDetail                = (LinearLayout) _view.findViewById(R.id.llDetailContainer_FPU);
        llMoreDetail            = (LinearLayout) _view.findViewById(R.id.llMoreDetailContainer_FPU);
        llDeac                  = (LinearLayout) _view.findViewById(R.id.llDesc_FPU);

        mCallingActivity.setEnableMenu(true);
        mCallingActivity.setTitle(mCurrentItem.getName());
    }

    private void setListener() {
        ivAddProduct.setOnClickListener(this);
        ivFicha.setOnClickListener(this);
        llMoreDetail.setOnClickListener(this);
        ivGoBack.setOnClickListener(this);
        ivDownScroll.setOnClickListener(this);
        makeDownloadListener();
    }

    private void makeDownloadListener() {
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + getActivity().getString(R.string.error), Toast.LENGTH_SHORT).show();
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

    private void getProduct() {
        if (mProductList == null) {
            mProductList = new ArrayList<Product>();
            for (Item item : mThirdList) {
                ApiManager.getProducts(mListener, item);
            }
        } else initProductDetail();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddProduct_FPU:
//                new CustomSpinerDialog(mCallingActivity, mCurrentItem).addProduct();
                AddProductToShopDialog
                        .newInstance(new ItemSerializable(mCurrentItem))
                        .show(mCallingActivity);

                break;
            case R.id.ivFichaCata_FPU:
                FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FichaFragment.newInstance(mCurrentItem.getFichaCata()));
                break;
//            case R.id.llMoreDetailContainer_FPU:
//                startSlideFragment(0);
//                break;
            case R.id.iv_back_FPU:
                super.getActivity().onBackPressed();
                break;
            case R.id.iv_downScroll_FP:
                downScrollList();
                break;
        }
    }

    private void verifyFichaCata() {
        if (mCurrentItem.getFichaCata() == null) {
            ivFicha.setVisibility(View.GONE);
        }
    }

    private void downScrollList(){
        int position = lvProductVideo.getFirstVisiblePosition();
        lvProductVideo.setSelection(++position);
    }

    private void initProductDetail() {
        if (mCurrentItem.getExtraVideos() != null && !mCurrentItem.getExtraVideos().isEmpty()) {
            setProductVideoDetail();
        } else {
            setProductNoVideoDetail();
        }

    }

    private void setProductVideoDetail() {
        lvProductVideo.setVisibility(View.VISIBLE);
        llMoreDetail.setVisibility(View.VISIBLE);
        rlProductPhotoContainer.setVisibility(View.GONE);
        initVideoList();
        initHorizontalImageList();
    }

    private void setProductNoVideoDetail() {
        tvProductPhotoTitle.setVisibility(View.VISIBLE);
        if (mThirdList == null)
            return;
        if (mThirdList.size() == 1) {
            setOneProductDetail();
        } else {
            llMoreDetail.setVisibility(View.GONE);
            calculateContainerSizeIfProductNoDetail();
            hlvAllProduct.setVisibility(View.VISIBLE);
            initHorizontalImageList();
        }
    }

    private void calculateContainerSizeIfProductNoDetail() {
        int companyLogoWidth = getDisplayWidth() / 8 * 2;
        int productDetailWidth = getDisplayWidth() / 8 * 6;
        final LinearLayout.LayoutParams companyLogoParams = new LinearLayout.LayoutParams(companyLogoWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        final LinearLayout.LayoutParams productDetailParams = new LinearLayout.LayoutParams(productDetailWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        llCompanyLogo.setLayoutParams(companyLogoParams);
        llDetail.setLayoutParams(productDetailParams);
    }

    private void setOneProductDetail() {
        llMoreDetail.setVisibility(View.VISIBLE);
        hlvAllProduct.setVisibility(View.GONE);
        lvProductVideo.setVisibility(View.GONE);
        ivProductPhoto.setImageBitmap(BitmapCreator.getBitmap(mProductList.get(0).getImage()));
        tvProductPhotoTitle.setText(mCallingActivity.getString(R.string.plus_info));

        llDeac.setPadding(0, 0, 0, mCallingActivity.getResources().getInteger(R.integer.desc_padding_bottom));
    }

    private void initHorizontalImageList() {
        if (mProductList.size() == 0) {
            hlvAllProduct.setVisibility(View.GONE);
            return;
        }
        final HorizontalPhotoProductAdapter adapter = new HorizontalPhotoProductAdapter(mCallingActivity, mProductList);
        if (mCurrentItem.getDescription() == null || mCurrentItem.getDescription().equals("<span style='font-family: Helvetica Neue, Helvetica, Arial, sans-serif;'></span>")) {
            adapter.setItemMargin(mCallingActivity.getResources().getInteger(R.integer.product_slide_margin));
        }
        hlvAllProduct.setAdapter(adapter);
        hlvAllProduct.setOnItemClickListener(this);
    }

    private void initVideoList() {
        llVideo.setBackground(BitmapCreator.getDrawable(mCurrentItem.getExtraBackgroundImage()));
        final ProductVideoAdapter apter = new ProductVideoAdapter(mCallingActivity, mCurrentItem);
        lvProductVideo.setAdapter(apter);
        lvProductVideo.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().getClass().getName().equals(ProductVideoAdapter.class.getName())) {

            startVideoActtivity(mCurrentItem.getExtraVideos().get(position));
           // startVideoPlayer(BitmapCreator.getAbsolutePath(mCurrentItem.getExtraVideos().get(position)));
        }


        if (parent.getAdapter().getClass().getName().equals(HorizontalPhotoProductAdapter.class.getName())) {
            startSlideFragment(position);
        }

    }

    private void startVideoActtivity(String _path) {
        Intent intent = new Intent(mCallingActivity, PlayVideoActivity.class);
        intent.putExtra(Constants.YOUTUBE_VIDEO_ID, _path);
        startActivity(intent);
    }

    private void startVideoPlayer(String _path) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_path));
        intent.setDataAndType(Uri.parse(_path), Constants.ViDEO_TYPE_DATA);
        startActivity(Intent.createChooser(intent, mCallingActivity.getString(R.string.not_play)));
//        startActivity(intent);
    }


    private void makeData() {
        ivCompanyLogo.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.getLogo()));
        mCallingActivity.setTitle(mCurrentItem.getName());
        mCallingActivity.setBackground(mCurrentItem.getBackgroundImage());

        if (mCurrentItem.getDescription() == null || mCurrentItem.getDescription().equals("<span style='font-family: Helvetica Neue, Helvetica, Arial, sans-serif;'></span>")) {
            wvProductDescription.setVisibility(View.GONE);
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

    private int getDisplayWidth() {
        return mCallingActivity.getWindowManager().getDefaultDisplay().getWidth();
    }

    private void startSlideFragment(int _position) {
        FragmentReplacer.replaceFragmentWithStack(
                mCallingActivity,
                FragmentSlide.newInstance(mProductList, _position));
    }


}
