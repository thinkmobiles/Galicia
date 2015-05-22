package com.galicia.galicia.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    public void updateList(List<Item> _list){
        this.itemsData = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product_cart_list, parent, false);
            holder = new ViewHolder();
            holder.initHolder(convertView);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.setData(BitmapCreator.getDrawable(itemsData.get(position).getIcon()), itemsData.get(position).getName());
        holder.sendButton.setOnClickListener(this);
        holder.deleteButton.setOnClickListener(deleteOnClickListener(position));
        return convertView;
    }

    private View.OnClickListener deleteOnClickListener(final int pos){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDAO itemDAO = new ItemDAO(context);
                itemDAO.deleteItem(itemsData.get(pos));
                List<Item> list = itemDAO.getItems(shop_id);
                updateList(list);
                Toast.makeText(context, R.string.delete_item, Toast.LENGTH_SHORT).show();
            }
        };
        return listener;
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

        public void initHolder(View _view){
            this.previewImage   = (ImageView) _view.findViewById(R.id.ivPrevProduct);
            this.nameItem       = (TextView) _view.findViewById(R.id.tv_title_goods_IS);
            this.deleteButton   = (ImageView) _view.findViewById(R.id.iv_delete_shop_item_IS);
            this.sendButton     = (ImageView) _view.findViewById(R.id.iv_send_shop_item_IS);
        }

        public void setData(Drawable _icon, String _name){
            previewImage.setImageDrawable(_icon);
            nameItem.setText(_name);
        }
    }

}
