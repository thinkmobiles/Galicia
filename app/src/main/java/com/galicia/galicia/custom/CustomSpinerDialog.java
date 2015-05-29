package com.galicia.galicia.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.adapters.SpinnerPurchaseAdapter;
import com.galicia.galicia.models.Shop;
import com.galicia.galicia.untils.DataBase.ItemDAO;
import com.galicia.galicia.untils.DataBase.ShopDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feltsan on 21.05.2015.
 */
public class CustomSpinerDialog {

    private Activity mCallingActivity;
    private LayoutInflater inflater;
    private ArrayList<Item> items;
    private int selected;
    private ShopDAO shopDAO;
    private ItemDAO itemDAO;
    private List<Shop> shopList, subList;
    private LinearLayout spinerLayout;
    private Spinner spinner;
    private SpinnerPurchaseAdapter spinnerPurchaseAdapter;
    private TextView positivButton, negativButton;
    private EditText shopName;
    private AlertDialog alertDialog;
    private Item mCurentItem;
    private AlertDialog.Builder spinerDialog;

    public CustomSpinerDialog(Activity activity, Item item) {
        mCallingActivity = activity;
        mCurentItem = item;

        shopDAO = new ShopDAO(activity);
        itemDAO = new ItemDAO(activity);
    }

    public void addProduct() {
        new GetShopTask().execute();
    }

    public void createCustomDialog() {
        spinerDialog = new AlertDialog.Builder(mCallingActivity);

        inflater = (LayoutInflater) mCallingActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog_spinner, null);

        findDialogUI(view);
        setDialogListener();

//        spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(mCallingActivity, subList);
        spinner.setAdapter(spinnerPurchaseAdapter);

        spinerDialog.setView(view);
        alertDialog = spinerDialog.create();
        alertDialog.show();

    }

    private void findDialogUI(View view) {
        spinerLayout  = (LinearLayout) view.findViewById(R.id.ll_spinner);
        spinner       = (Spinner) view.findViewById(R.id.dialogSpinner);
        negativButton = (TextView) view.findViewById(R.id.tv_cancel_action_CD);
        positivButton = (TextView) view.findViewById(R.id.tv_accept_action_CD);
        shopName      = (EditText) view.findViewById(R.id.et_new_Shop_CD);
    }

    private void setDialogListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                if (subList.get(position).getId() == -1) {
                    spinerLayout.setVisibility(View.INVISIBLE);
                    shopName.setVisibility(View.VISIBLE);
                }
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
                    if (subList.get(selected).getId() == -1) {
                        spinerLayout.setVisibility(View.INVISIBLE);
                        shopName.setVisibility(View.VISIBLE);
                    } else {
                        shopList.get(selected).getId();
                        itemDAO.save(mCurentItem, subList.get(selected).getId());
                        alertDialog.dismiss();
                        Toast.makeText(mCallingActivity, "Item add to shop_id= " + String.valueOf(subList.get(selected).getId()), Toast.LENGTH_SHORT).show();

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

    public class GetShopTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            shopList = new ArrayList<>();
            subList = new ArrayList<>();
            shopList = shopDAO.getShops();
            if (!shopList.isEmpty()) {
                Shop lastElement = shopList.get(shopList.size() - 1);
                shopList.add(0, lastElement);
                subList = shopList.subList(0, shopList.size() - 1);
            }

            subList.add(new Shop(-1, "Create new shop"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            createCustomDialog();
        }
    }

}
