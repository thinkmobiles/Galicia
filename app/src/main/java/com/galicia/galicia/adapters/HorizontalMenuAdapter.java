package com.galicia.galicia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.galicia.galicia.R;

import java.util.List;

public class HorizontalMenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    public HorizontalMenuAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_horizontal, null);
            holder = new Holder();
            holder.header = (ImageView) convertView.findViewById(R.id.ivImageMenuProduct);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

//        holder.header.setText(mList.get(position));
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private class Holder {
        ImageView header;
    }
}
