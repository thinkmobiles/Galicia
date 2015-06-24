package com.cristaliza.alimentacion.untils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cristaliza.alimentacion.R;

import java.util.List;

/**
 * Created by Bogdan on 06.05.2015.
 */
public class MenuAdapter extends BaseAdapter {

    private List<String> list;
    private Context mContext;

    public MenuAdapter(List<String> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu,null);
            holder.textView = (TextView) convertView.findViewById(R.id.tvMenuItem);
            convertView.setTag(position);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class Holder {
        TextView textView;
    }

}
