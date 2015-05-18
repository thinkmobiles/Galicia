package com.galicia.galicia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.global.ItemsPurchaseList;

import java.util.ArrayList;

/**
 * Created by Feltsan on 18.05.2015.
 */
public class SpinnerPurchaseAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater inflater;

    public SpinnerPurchaseAdapter(Context _context, ArrayList<Item>_data){
        context = _context;
        items = _data;

        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
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

            holder.nameItem = (TextView) convertView.findViewById(R.id.tv_titleItemSpinner_IS);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

            holder.nameItem.setText(items.get(position).getName());
            holder.nameItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.set(position,getItem(position));
                }
            });

            return convertView;

    }

    private class ViewHolder{
        TextView nameItem;
    }
}
