package com.galicia.galicia.adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.models.estrella.Item;
import com.galicia.galicia.R;
import com.galicia.galicia.fragments.ItemCartFragment;
import com.galicia.galicia.fragments.ShopCartFragment;
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.models.Shop;
import com.galicia.galicia.untils.DataBase.ItemDAO;
import com.galicia.galicia.untils.DataBase.ShopDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ShopCartAdapter extends BaseAdapter{
    private FragmentActivity activity;
    private List<Shop> shopsData;
    private LayoutInflater inflater;

    public ShopCartAdapter(FragmentActivity activity, List<Shop> _data) {
        this.activity = activity;
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

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_shoping_cart_list, parent, false);
            holder = new ViewHolder();

            holder.nameShop = (TextView) convertView.findViewById(R.id.tv_title_goods_IS);
            holder.refreshButton = (ImageView) convertView.findViewById(R.id.iv_refresh_shop_item_IS);
            holder.deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_shop_item_IS);
            holder.seeButton = (ImageView) convertView.findViewById(R.id.iv_see_detail_shop_item_IS);
            holder.sendButton = (ImageView) convertView.findViewById(R.id.iv_send_shop_item_IS);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.nameShop.setText(shopsData.get(position).getName());

        holder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDAO i =new ItemDAO(activity);
                List<Item> items = i.getItems(String.valueOf(shopsData.get(position).getId()));
                ArrayList<String> list = new ArrayList<String>();
                for (Item item:items){
                    list.add(item.getPdf());
                }
                openEmailClient(list);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDAO shopDAO = new ShopDAO(activity);
                shopDAO.deleteShop(shopsData.get(position));
                ShopCartFragment.newInstance().updateDate();
                notifyDataSetChanged();
                Toast.makeText(activity, R.string.delete_shop, Toast.LENGTH_SHORT).show();
            }
        });


        holder.seeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentReplacer.replaceFragmentWithStack(activity,
                        ItemCartFragment.newInstance(String.valueOf(shopsData.get(position).getId()), shopsData.get(position).getName()));
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView nameShop;
        private ImageView refreshButton, deleteButton, seeButton, sendButton;
    }
    public void openEmailClient(ArrayList<String> strings){
        Intent mailer = new Intent(Intent.ACTION_SEND);
        mailer.setType("text/plain");
        mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"name@email.com"});
        mailer.putExtra(Intent.EXTRA_SUBJECT, strings);
        //  mailer.putExtra(Intent.EXTRA_TEXT, bodyText);
        activity.startActivity(Intent.createChooser(mailer, "Send email..."));
    }
}
