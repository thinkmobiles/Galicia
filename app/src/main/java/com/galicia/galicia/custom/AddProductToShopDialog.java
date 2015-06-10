package com.galicia.galicia.custom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.galicia.galicia.fragments.ShopCartFragment;
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
    private TextView tvAccept, tvCancel, tvTitle;
    private EditText shopName;
    private Item mCurrentItem;
    private FrameLayout flTop, flBottom;
    private EventListener mListener;
    private List<Product> mProductList;
    private List<Item> mThirdList;
    private AutoCompleteTextView autoCompleteTextView;
    private ImageView allShop;
    private boolean isSelectChek, questionCheck=false;
    private boolean isShowListShop = false;
    private int typeDialog;
    private FragmentProduct mFragmentBack;
    ArrayAdapter<String> adapter;

    public static AddProductToShopDialog newInstance(final ItemSerializable _item) {
        final AddProductToShopDialog fragment = new AddProductToShopDialog();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ITEM_SERIAZ, _item);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void show(MainActivity _mActivity,FragmentProduct _fragment, int _typeDialog){
        mFragmentBack = _fragment;
        typeDialog = _typeDialog;
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
        if(typeDialog == Constants.TYPE_DIALOG_ADD) {
            addProduct();
            setViewSettings();
        } else {
            questionCheck = true;
            setVisible();
        }
        mCallingActivity.setEnableMenu(false);
        return inflaterView;
    }

    private void initSpinner(){
        allShop.setVisibility(View.VISIBLE);

        String[] names = new String[subList.size()];
        for (int i = 0; i<subList.size(); i++ ){
            names[i] = subList.get(i).getName();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, names);
        spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(mCallingActivity, subList);
        spinner.setAdapter(spinnerPurchaseAdapter);
        autoCompleteTextView.setAdapter(adapter);
    }

    public void setVisible(){
        tvTitle.setText(mCallingActivity.getString(R.string.want_continue));
        tvCancel.setText(mCallingActivity.getString(R.string.ver_envio));
        tvAccept.setText(mCallingActivity.getString(R.string.continuar));
        allShop.setVisibility(View.GONE);
        autoCompleteTextView.setVisibility(View.GONE);

    }

    private void findViews(final View _view) {
        tvTitle         = (TextView) _view.findViewById(R.id.tv_dialogTitle_CD);
        spinnerLayout   = (LinearLayout) _view.findViewById(R.id.llSpinner_PSD);
        spinner         = (Spinner) _view.findViewById(R.id.spinner_PSD);
        tvCancel        = (TextView) _view.findViewById(R.id.tvCancel_PSD);
        tvAccept        = (TextView) _view.findViewById(R.id.tvAccept_PSD);
        flTop           = (FrameLayout) _view.findViewById(R.id.flTop_PSD);
        flBottom        = (FrameLayout) _view.findViewById(R.id.flBottom_PSD);
        autoCompleteTextView = (AutoCompleteTextView) _view.findViewById(R.id.etNewShop_PSD1);
        allShop         = (ImageView) _view.findViewById(R.id.iv_all_ItemShop_PSD);
    }
    private void setViewSettings(){
        autoCompleteTextView.setDropDownBackgroundResource(R.color.bg_grey);
        if(subList.isEmpty())
            allShop.setVisibility(View.GONE);
    }

    private void setListeners() {
        spinner.setOnItemSelectedListener(this);
        tvCancel.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        flBottom.setOnClickListener(this);
        flTop.setOnClickListener(this);
        allShop.setOnClickListener(this);
        makeDownloadListener();
        makeAutocompleteItemClickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancel_PSD:
                if (questionCheck){
                    FragmentReplacer.popSupBackStack(getActivity());
                    FragmentReplacer.replaceFragmentWithStack(getActivity(), new ShopCartFragment());
                    questionCheck = false;
                } else {
                    FragmentReplacer.popSupBackStack(getActivity());
                }
                break;
            case R.id.tvAccept_PSD:
                if(questionCheck) {
                    FragmentReplacer.popSupBackStack(getActivity());
                    questionCheck = false;
                } else
                     onClickPositiveButton();
                break;
            case R.id.flTop_PSD:
                break;
            case R.id.flBottom_PSD:
                FragmentReplacer.popSupBackStack(getActivity());
                break;
            case R.id.iv_all_ItemShop_PSD :
                changeDownUpList();
                break;
        }
    }

    private void changeDownUpList(){
        if(isShowListShop){
            autoCompleteTextView.dismissDropDown();
        }else{
            autoCompleteTextView.showDropDown();
        }
        isShowListShop = !isShowListShop;
    }

    private void onClickPositiveButton(){
//        if (shopName.getVisibility() == View.GONE) {
//            if (subList.get(selected).getId() == null) {
//                spinnerLayout.setVisibility(View.INVISIBLE);
//                shopName.setVisibility(View.VISIBLE);
//            } else {


//            }

         if (!autoCompleteTextView.getText().toString().isEmpty()) {
             questionCheck = true;
             if (isSelectChek) {
                shopList.get(selected).getId();

                     DBManager.addItem(
                             mCurrentItem.getPdf(),
                             subList.get(selected),
                             mCurrentItem.getName(),
                             mCurrentItem.getIcon()
                     );

                isSelectChek = false;
                mFragmentBack.setTypeDialog(Constants.TYPE_DIALOG_ADDED);
                setVisible();
             } else {
                 Shop shop = DBManager.addShop(autoCompleteTextView.getText().toString());
                 spinnerLayout.setVisibility(View.VISIBLE);
                 addProduct();


                     DBManager.addItem(
                             mCurrentItem.getPdf(),
                             shop,
                             mCurrentItem.getName(),
                             mCurrentItem.getIcon());


//                 ApiManager.getThirdLevel(mListener, mCurrentItem);
                 Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.add_shop_succesfull),Toast.LENGTH_SHORT ).show();
                 autoCompleteTextView.setText("");
                 adapter.notifyDataSetChanged();
                 autoCompleteTextView.showDropDown();
                 mFragmentBack.setTypeDialog(Constants.TYPE_DIALOG_ADDED);
                 setVisible();
             }

         }else {
             Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.enter_shop), Toast.LENGTH_SHORT).show();
         }

//            spinnerLayout.setVisibility(View.VISIBLE);
//            shopName.setVisibility(View.GONE);
           // addProduct();
//            FragmentReplacer.popSupBackStack(getActivity());
//        } else {
//            Toast.makeText(mCallingActivity, "enter shop", Toast.LENGTH_SHORT).show();

    }
    private void addProductToCart(){
        ApiManager.getThirdLevel(mListener, mCurrentItem);
//        FragmentReplacer.popSupBackStack(getActivity());
        Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.add_item_to_shop) + String.valueOf(subList.get(selected).getId()), Toast.LENGTH_SHORT).show();
    }

    private void makeDownloadListener() {
        mListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + mCallingActivity.getString(R.string.error), Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mThirdList = ApiManager.getThirdList();
                        getProduct();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProductList.add(ApiManager.getProductsList().get(0));
//                        if (mProductList.size() == mThirdList.size()){
//                            DBManager.addItem(
//                                    mCurrentItem.getPdf(),
//                                    subList.get(selected),
//                                    mCurrentItem.getName(),
//                                    mProductList
//                            );
//                        }

                }
            }
        };
    }

    private void makeAutocompleteItemClickListener(){
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                isSelectChek=true;
            }
        });
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
//        subList.add(new Shop("Create new shop"));

        initSpinner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallingActivity.setEnableMenu(true);
    }
}
