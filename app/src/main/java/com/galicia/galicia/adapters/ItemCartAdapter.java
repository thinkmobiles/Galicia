package com.galicia.galicia.adapters;

import android.content.Context;
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
import com.galicia.galicia.global.FragmentReplacer;
import com.galicia.galicia.untils.BitmapCreator;
import com.galicia.galicia.untils.DataBase.ItemDAO;

import java.util.List;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ItemCartAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<Item> itemsData;
    private LayoutInflater inflater;
    private String shop_id;


    public ItemCartAdapter(Context _context, List<Item> _data, String _shop_id) {
        if (_context != null) {
            context = _context;
            itemsData = _data;
            shop_id = _shop_id;

            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    @Override
    public int getCount() {
        return itemsData.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product_cart_list, parent, false);
            holder = new ViewHolder();

            holder.previewImage = (ImageView) convertView.findViewById(R.id.ivPrevProduct);
            holder.nameItem = (TextView) convertView.findViewById(R.id.tv_title_goods_IS);
            holder.deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_shop_item_IS);
            holder.sendButton = (ImageView) convertView.findViewById(R.id.iv_send_shop_item_IS);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.previewImage.setImageDrawable(BitmapCreator.getDrawable(itemsData.get(position).getIcon()));
        holder.nameItem.setText(itemsData.get(position).getName());

        holder.sendButton.setOnClickListener(this);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDAO itemDAO = new ItemDAO(context);
                itemDAO.deleteItem(itemsData.get(position));
                FragmentReplacer.replaceFragmentWithoutBackStack((android.support.v4.app.FragmentActivity) context, ItemCartFragment.newInstance(shop_id));
//                    fragment.updateDate();
                //                  notifyDataSetChanged();
                Toast.makeText(context, R.string.delete_item, Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_shop_item_IS:
                break;
        }
    }

    class ViewHolder {
        private TextView nameItem;
        private ImageView previewImage, deleteButton, sendButton;
    }

}
