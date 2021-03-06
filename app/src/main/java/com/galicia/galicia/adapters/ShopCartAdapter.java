package com.galicia.galicia.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.galicia.galicia.R;
import com.galicia.galicia.fragments.ItemCartFragment;
import com.galicia.galicia.fragments.ShopCartFragment;
import com.galicia.galicia.global.FragmentReplacer;

import com.galicia.galicia.orm_database.DBManager;
import com.galicia.galicia.orm_database.Shop;
import com.galicia.galicia.untils.PDFSender;

import java.util.List;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ShopCartAdapter extends BaseAdapter {
    private FragmentActivity activity;
    private List<Shop> shopsData;
    private LayoutInflater inflater;
    private ShopCartFragment fragment;

    public ShopCartAdapter(FragmentActivity activity, List<Shop> _data, ShopCartFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        shopsData = _data;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return shopsData.size();
    }

    @Override
    public Shop getItem(int position) {
        return shopsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateList(List<Shop> _list){
        this.shopsData = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_shoping_cart_list, parent, false);
            holder = new ViewHolder();
            holder.initHolder(convertView);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.setData(getItem(position).getName());
        holder.setListener(position);

        return convertView;
    }

    class ViewHolder {
        private TextView nameShop;
        private ImageView deleteButton, seeButton, sendButton;

        public void initHolder(View view) {
            nameShop = (TextView) view.findViewById(R.id.tv_title_goods_IS);
            deleteButton = (ImageView) view.findViewById(R.id.iv_delete_shop_item_IS);
            seeButton = (ImageView) view.findViewById(R.id.iv_see_detail_shop_item_IS);
            sendButton = (ImageView) view.findViewById(R.id.iv_send_shop_item_IS);
        }

        public void setListener(int position) {
            sendButton.setOnClickListener(shopItemListener(position));
            deleteButton.setOnClickListener(shopItemListener(position));
            seeButton.setOnClickListener(shopItemListener(position));
        }

        public void setData(String name) {
            nameShop.setText(name);
        }
    }

    private void deleteShop(int _pos) {
        DBManager.deleteShop(getItem(_pos));
        updateList(DBManager.getShops());
        fragment.updateDate();
        Toast.makeText(activity, R.string.delete_shop, Toast.LENGTH_SHORT).show();
    }

    private void seeDetailItem(int _pos) {
        FragmentReplacer.replaceFragmentWithStack(activity,
                ItemCartFragment.newInstance(getItem(_pos).getId(), getItem(_pos).getName()));
    }

    private void sendPDF(int _pos) {
        PDFSender.sendShopPDFs(activity, getItem(_pos).getId());
    }

    private View.OnClickListener shopItemListener(final int _pos) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_delete_shop_item_IS:
                        deleteShop(_pos);
                        break;
                    case R.id.iv_send_shop_item_IS:
                        sendPDF(_pos);
                        break;
                    case R.id.iv_see_detail_shop_item_IS:
                        seeDetailItem(_pos);
                        break;
                }
            }
        };
    }

}
