package com.galicia.galicia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.galicia.galicia.R;
import com.galicia.galicia.orm_database.Shop;

import java.util.List;

/**
 * Created by Feltsan on 18.05.2015.
 */
public class SpinnerPurchaseAdapter extends BaseAdapter {
    private Context context;
    private List<Shop> shops;
    private LayoutInflater inflater;

    public SpinnerPurchaseAdapter(Context _context, List<Shop> _data){
        context = _context;
        shops = _data;

        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Shop getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_spiner,parent,false);
            holder = new ViewHolder();

            holder.nameShop = (TextView) convertView.findViewById(R.id.tv_titleShopSpinner_IS);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

            holder.nameShop.setText(shops.get(position).getName());

            return convertView;

    }

    private class ViewHolder{
        TextView nameShop;
    }
}
