package com.galicia.galicia.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.galicia.galicia.R;
import com.galicia.galicia.fragments.ItemCartFragment;
import com.galicia.galicia.fragments.ShopCartFragment;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.Shop;
import com.galicia.galicia.untils.DataBase.ShopDAO;

import java.util.List;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ShopCartAdapter extends BaseAdapter implements View.OnClickListener {
    private FragmentActivity activity;
    private List<Shop> shopsData;
    private LayoutInflater inflater;

    public ShopCartAdapter(FragmentActivity activity, List<Shop> _data){
            this.activity = activity;
            shopsData = _data;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return shopsData.size();
    }

    @Override
    public Object getItem(int position) {
        return shopsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_shoping_cart_list,parent,false);
            holder = new ViewHolder();

            holder.nameShop = (TextView) convertView.findViewById(R.id.tv_title_goods_IS);
            holder.refreshButton = (ImageView) convertView.findViewById(R.id.iv_refresh_shop_item_IS);
            holder.deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_shop_item_IS);
            holder.seeButton = (ImageView) convertView.findViewById(R.id.iv_see_detail_shop_item_IS);
            holder.sendButton = (ImageView) convertView.findViewById(R.id.iv_send_shop_item_IS);

            convertView.setTag(holder);

        }else
            holder = (ViewHolder) convertView.getTag();

            holder.nameShop.setText(shopsData.get(position).getName());

            holder.sendButton.setOnClickListener(this);
            holder.deleteButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopDAO shopDAO = new ShopDAO(activity);
                    shopDAO.deleteShop(shopsData.get(position));
                    notifyDataSetChanged();
                }
            });

            holder.refreshButton.setOnClickListener(this);
            holder.seeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentReplacer.replaceFragmentWithStack(activity,
                            ItemCartFragment.newInstance(String.valueOf(shopsData.get(position).getId())));
                }
            });

        return convertView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_send_shop_item_IS :
                break;
            case R.id.iv_refresh_shop_item_IS :
                break;
            case R.id.iv_see_detail_shop_item_IS :

                break;
        }
    }

    class ViewHolder{
        private TextView nameShop;
        private ImageView refreshButton, deleteButton, seeButton, sendButton;
    }

}
