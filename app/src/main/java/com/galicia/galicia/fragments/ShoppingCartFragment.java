package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.galicia.galicia.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feltsan on 12.05.2015.
 */
public class ShoppingCartFragment extends Fragment {
//    private ShoppingCartAdapter shoppingCartAdapter;
    private List<String> data;
    private ListView shopingList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        data = new ArrayList<>();
        data.add("Beer");
        data.add("Vodka");
        data.add("Vine");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart_shopping,container,false);

        shopingList = (ListView) rootView.findViewById(R.id.lv_list_Shopping_FS);

//        shoppingCartAdapter = new ShoppingCartAdapter(getActivity(),getActivity().getApplicationContext(),data);
//        shopingList.setAdapter(shoppingCartAdapter);
        return rootView;
    }
}
