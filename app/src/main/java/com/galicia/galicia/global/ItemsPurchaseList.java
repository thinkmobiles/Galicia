package com.galicia.galicia.global;

import android.content.Context;

import com.cristaliza.mvc.models.estrella.Item;

import java.util.ArrayList;

/**
 * Created by Feltsan on 18.05.2015.
 */
public class ItemsPurchaseList {

    private ArrayList<Item> mItems;
    private static ItemsPurchaseList sPurchaseItems;
    private Context context;

    private ItemsPurchaseList(Context appContext){
        context = appContext;
        mItems = new ArrayList<>();
    }

    public static synchronized ItemsPurchaseList getInstance (Context context){
        if(sPurchaseItems == null){
            sPurchaseItems = new ItemsPurchaseList(context.getApplicationContext());
        }
        return sPurchaseItems;
    }

    public void addItem(Item item){
        mItems.add(item);
    }

    public void deleteItem(final int position){
        mItems.remove(position);
    }

    public void moveItem(final int position, Item item){
        mItems.add(position, item);
    }

    public ArrayList<Item> getItems(){
        return mItems;
    }

    public int getSize(){
        return mItems.size();
    }

    public void clearItems(){
        mItems.clear();
    }

}
