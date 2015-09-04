package com.cristaliza.alimentation.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.alimentation.fragments.ItemCartFragment;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.alimentation.MainActivity;
import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.adapters.SpinnerPurchaseAdapter;
import com.cristaliza.alimentation.global.Constants;
import com.cristaliza.alimentation.global.FragmentReplacer;
import com.cristaliza.alimentation.models.ItemSerializable;
import com.cristaliza.alimentation.orm_database.DBManager;
import com.cristaliza.alimentation.orm_database.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasia on 26.05.2015.
 */
public class AddProductToShopDialog extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private MainActivity mCallingActivity;
    private int selected;

    private List<Shop> subList;
    private LinearLayout spinnerLayout;
    private Spinner spinner;
    private TextView tvAccept, tvCancel, tvTitle;
    private Item mCurrentItem;
    private FrameLayout flTop, flBottom, flcenter;
    private AutoCompleteTextView autoCompleteTextView;
    private ImageView allShop;
    private boolean isSelectChek, questionCheck=false;
    private boolean isShowListShop = false;
    private int typeDialog;
    private Shop mShop;
    private ArrayAdapter<String> adapter;

    public static AddProductToShopDialog newInstance(final ItemSerializable _item) {
        final AddProductToShopDialog fragment = new AddProductToShopDialog();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ITEM_SERIAZ, _item);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void show(MainActivity _mActivity, int _typeDialog){
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
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, names);
        spinner.setAdapter(new SpinnerPurchaseAdapter(mCallingActivity, subList));
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
        flcenter        = (FrameLayout) _view.findViewById(R.id.flBackground_PSD);
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
        flcenter.setOnClickListener(this);
        flTop.setOnClickListener(this);
        allShop.setOnClickListener(this);
        makeAutocompleteItemClickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancel_PSD:
                if (questionCheck){
                    FragmentReplacer.popSupBackStack(getActivity());
                    FragmentReplacer.replaceFragmentWithStack(
                            mCallingActivity,
                            ItemCartFragment.newInstance(mShop.getId(), mShop.getName())
                    );
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
            case R.id.flBackground_PSD:
                break;
            case R.id.flTop_PSD:
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
            allShop.setImageResource(R.drawable.d_arrow);
        }else{
            autoCompleteTextView.showDropDown();
            allShop.setImageResource(R.drawable.u_arrow);
        }
        isShowListShop = !isShowListShop;
    }

    private void onClickPositiveButton(){
         if (!autoCompleteTextView.getText().toString().isEmpty()) {
             questionCheck = true;
             if (isSelectChek) {
                mShop = subList.get(selected);
                     DBManager.addItem(
                             mCurrentItem.getPdf(),
                             mShop,
                             mCurrentItem.getName(),
                             mCurrentItem.getIcon()
                     );

                isSelectChek = false;
                setVisible();
             } else {
                 Shop shop = DBManager.addShop(autoCompleteTextView.getText().toString());
                 mShop = shop;
                 spinnerLayout.setVisibility(View.VISIBLE);
                 addProduct();
                     DBManager.addItem(
                             mCurrentItem.getPdf(),
                             shop,
                             mCurrentItem.getName(),
                             mCurrentItem.getIcon());
                 Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.add_shop_succesfull),Toast.LENGTH_SHORT ).show();
                 autoCompleteTextView.setText("");
                 adapter.notifyDataSetChanged();
                 autoCompleteTextView.showDropDown();
                 setVisible();
             }

         }else {
             Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.enter_shop), Toast.LENGTH_SHORT).show();
         }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addProduct(){
        subList = new ArrayList<>();
        List<Shop> shopList = DBManager.getShops();
        if (!shopList.isEmpty()) {
            Shop lastElement = shopList.get(shopList.size() - 1);
            shopList.add(0, lastElement);
            subList = shopList.subList(0, shopList.size() - 1);
        }
        initSpinner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallingActivity.setEnableMenu(true);
    }
}
