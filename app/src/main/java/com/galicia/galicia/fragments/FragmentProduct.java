package com.galicia.galicia.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    private ImageView ivProductPhoto, ivCompanyLogo, ivAddProduct, ivFicha, ivGoBack, ivDownScroll, ivProductAward, ivNext, ivPrev;
    private HorizontalScrollView hsvList;
    private ListView lvProductVideo;
    private TextView tvProductPhotoTitle;
    private WebView wvProductDescription;
    private EventListener mListener;
    private ArrayList<Product> mProductList;
    private List<Item> mThirdList;
    private RelativeLayout rlProductPhotoContainer, rlDownScroll, rlNext, rlPrev, rlContHSV, rlContWeb;
    private LinearLayout llMoreDetail, llVideo, llDeac, llContProd;

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

    @Override
    public void onResume() {
        super.onResume();
        rlContHSV.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int maxWidth = getDisplayWidth() / 2;
        if(bigPart){
            maxWidth = getDisplayWidth() / 4 * 3;
        }
        if(mThirdList.size() >= 1) {
            long s = -100;
            for (int i = 0; i < llContProd.getChildCount(); ++i) {
                s = s + llContProd.getChildAt(i).getMeasuredWidth() + 30;
            }
            if (s < maxWidth) {
                rlNext.setVisibility(View.GONE);
                rlPrev.setVisibility(View.GONE);
            }
        }
    }

    private void findUI(final View _view) {
        ivAddProduct = (ImageView) _view.findViewById(R.id.ivAddProduct_FPU);
        ivFicha = (ImageView) _view.findViewById(R.id.ivFichaCata_FPU);
        ivGoBack = (ImageView) _view.findViewById(R.id.iv_back_FPU);
        ivDownScroll = (ImageView) _view.findViewById(R.id.iv_downScroll_FP);
        ivCompanyLogo = (ImageView) _view.findViewById(R.id.ivCompanyLogo_FPU);
        ivProductPhoto = (ImageView) _view.findViewById(R.id.ivProductPhoto_FPU);
        ivProductAward = (ImageView) _view.findViewById(R.id.ivCompanyAward_FPU);
        ivPrev = (ImageView) _view.findViewById(R.id.ivPrev_FPU);
        ivNext = (ImageView) _view.findViewById(R.id.ivNext_FPU);
        wvProductDescription = (WebView) _view.findViewById(R.id.wvProductDescription_FPU);
        tvProductPhotoTitle = (TextView) _view.findViewById(R.id.tvThirdProductTitle_FPU);
        hsvList = (HorizontalScrollView) _view.findViewById(R.id.hsvList);
        lvProductVideo = (ListView) _view.findViewById(R.id.lvProductVideo_FPU);
        rlProductPhotoContainer = (RelativeLayout) _view.findViewById(R.id.rlProductPhotoContainer_FPU);
        rlDownScroll = (RelativeLayout) _view.findViewById(R.id.rlDownScroll);
        rlPrev = (RelativeLayout) _view.findViewById(R.id.rlPrev_FPU);
        rlNext = (RelativeLayout) _view.findViewById(R.id.rlNext_FPU);
        rlContHSV = (RelativeLayout) _view.findViewById(R.id.rlContHSV);
        rlContWeb = (RelativeLayout) _view.findViewById(R.id.rlContWeb);
        llVideo = (LinearLayout) _view.findViewById(R.id.ll_video_container_FP);
        llMoreDetail = (LinearLayout) _view.findViewById(R.id.llMoreDetailContainer_FPU);
        llDeac = (LinearLayout) _view.findViewById(R.id.llDesc_FPU);
        llContProd = (LinearLayout) _view.findViewById(R.id.llContProd);

        mCallingActivity.setEnableMenu(true);
        mCallingActivity.setTitle(mCurrentItem.getName());
    }

    private void setListener() {
        ivAddProduct.setOnClickListener(this);
        ivFicha.setOnClickListener(this);
        rlProductPhotoContainer.setOnClickListener(this);
        ivGoBack.setOnClickListener(this);
        ivDownScroll.setOnClickListener(this);
        rlDownScroll.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        rlPrev.setOnClickListener(this);
        rlNext.setOnClickListener(this);
        makeDownloadListener();
    }

    private void makeDownloadListener() {
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThirdList = ApiManager.getThirdList();
                        getProduct();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList.add(ApiManager.getProductsList().get(0));
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
        initProductDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddProduct_FPU:
                AddProductToShopDialog
                        .newInstance(new ItemSerializable(mCurrentItem))
                        .show(mCallingActivity, this, Constants.TYPE_DIALOG_ADD);
                break;
            case R.id.ivFichaCata_FPU:
                FragmentReplacer.replaceFragmentWithStack(mCallingActivity, FichaFragment.newInstance(mCurrentItem.getFichaCata()));
                break;
            case R.id.rlProductPhotoContainer_FPU:
                startSlideFragment(0);
                break;
            case R.id.iv_back_FPU:
                super.getActivity().onBackPressed();
                break;
            case R.id.iv_downScroll_FP:
            case R.id.rlDownScroll:
                downScrollList();
                break;
            case R.id.ivPrev_FPU:
            case R.id.rlPrev_FPU:
                hsvList.pageScroll(View.FOCUS_LEFT);
                break;
            case R.id.ivNext_FPU:
            case R.id.rlNext_FPU:
                hsvList.pageScroll(View.FOCUS_RIGHT);
                break;
        }
    }

    private void verifyFichaCata() {
        if (mCurrentItem.getFichaCata() == null) {
            ivFicha.setVisibility(View.GONE);
        }
    }


    private void downScrollList() {
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
            calculateContainerSizeIfProductNoDetail();
            initHorizontalImageList();
        }
    }

    private boolean bigPart = false;

    private void calculateContainerSizeIfProductNoDetail() {
        bigPart = true;
        llMoreDetail.setVisibility(View.GONE);
    }

    private void setOneProductDetail() {
        llMoreDetail.setVisibility(View.VISIBLE);
        rlContHSV.setVisibility(View.GONE);
        lvProductVideo.setVisibility(View.GONE);
        ivProductPhoto.setImageBitmap(BitmapCreator.getBitmap(mProductList.get(0).getImage()));
        tvProductPhotoTitle.setText(mCallingActivity.getString(R.string.plus_info));

        llDeac.setPadding(0, 0, 0, mCallingActivity.getResources().getInteger(R.integer.desc_padding_bottom));
    }

    private void initHorizontalImageList() {
        if (mProductList.size() == 0) {
            rlContHSV.setVisibility(View.GONE);
            return;
        }

        if(llContProd.getChildCount() != 0){
            llContProd.removeAllViewsInLayout();
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mCallingActivity.getResources().getDimension(R.dimen.width_item_hlv_mini), ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mCurrentItem.getDescription() == null
                || mCurrentItem.getDescription().equals("<span style='font-family: Helvetica Neue, Helvetica, Arial, sans-serif;'></span>")) {
            params.setMargins(
                    mCallingActivity.getResources().getInteger(R.integer.product_slide_margin),
                    0,
                    mCallingActivity.getResources().getInteger(R.integer.product_slide_margin),
                    0);
        }

        for (int i = 0; i < mProductList.size(); ++i) {
            View view = View.inflate(mCallingActivity, R.layout.item_horizontal_list_product, null);
            ImageView image = (ImageView) view.findViewById(R.id.ivPhotoProd);
            image.setImageBitmap(BitmapCreator.getBitmap(mProductList.get(i).getImageSmall()));
            view.setLayoutParams(params);
            view.setOnClickListener(getListener(i));
            llContProd.addView(view);
        }
    }

    private View.OnClickListener getListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSlideFragment(position);
            }
        };
    }

    private void initVideoList() {
        llVideo.setBackgroundDrawable(BitmapCreator.getDrawable(mCurrentItem.getExtraBackgroundImage()));
        ProductVideoAdapter adapter = new ProductVideoAdapter(mCallingActivity, mCurrentItem);
        lvProductVideo.setAdapter(adapter);
        if(adapter.getCount() < 3)
            rlDownScroll.setVisibility(View.GONE);
        lvProductVideo.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().getClass().getName().equals(ProductVideoAdapter.class.getName())) {
            startVideoActtivity(mCurrentItem.getExtraVideos().get(position));
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

    private void makeData() {
        int koefWidthLogo = (int) mCallingActivity.getResources().getDimension(R.dimen.logo_width);
        Bitmap bitmap = BitmapCreator.getBitmap(mCurrentItem.getLogo());
        if(bitmap != null){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    koefWidthLogo,
                    bitmap.getHeight() * koefWidthLogo / bitmap.getWidth()
            );
            ivCompanyLogo.setLayoutParams(params);
        }
        ivCompanyLogo.setImageBitmap(bitmap);
        if (mCurrentItem.getPrizes() != null) {
            bitmap = BitmapCreator.getBitmap(mCurrentItem.getPrizes().get(0));
            if(bitmap.getHeight() < 450){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        koefWidthLogo,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                ivProductAward.setLayoutParams(params);
            }
            ivProductAward.setImageBitmap(bitmap);
        }

        mCallingActivity.setTitle(mCurrentItem.getName());
        mCallingActivity.setBackground(mCurrentItem.getBackgroundImage());

        if (mCurrentItem.getDescription() == null
                || mCurrentItem.getDescription().equals("<span style='font-family: Helvetica Neue, Helvetica, Arial, sans-serif;'></span>")) {
            rlContWeb.setVisibility(View.INVISIBLE);
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
