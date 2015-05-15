package com.galicia.galicia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.galicia.galicia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ShoppingCartAdapter extends BaseAdapter {
    private Context context;
    private List<String> titles;
    private LayoutInflater inflater;

    public ShoppingCartAdapter(Context _context, List<String> data){
        if(_context != null){
            context = _context;
            titles = data;

            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }
    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_shoping_cart_list,parent,false);
            holder = new ViewHolder();

            holder.titleGoods = (TextView) convertView.findViewById(R.id.tv_title_goods_IS);
            holder.refreshButton = (ImageView) convertView.findViewById(R.id.iv_refresh_shop_item_IS);
            holder.deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_shop_item_IS);
            holder.seeButton = (ImageView) convertView.findViewById(R.id.iv_see_detail_shop_item_IS);
            holder.sendButton = (ImageView) convertView.findViewById(R.id.iv_send_shop_item_IS);

            convertView.setTag(holder);

        }else
            holder = (ViewHolder) convertView.getTag();

            holder.titleGoods.setText(titles.get(position).toString());

        return convertView;
    }

    class ViewHolder{
        private TextView titleGoods;
        private ImageView refreshButton, deleteButton, seeButton, sendButton;
    }
}
