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
import com.galicia.galicia.untils.BitmapCreator;
import com.galicia.galicia.untils.DataBase.ItemDAO;
import com.galicia.galicia.untils.PDFSender;

import java.util.List;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ItemCartAdapter extends BaseAdapter {
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
    public Item getItem(int position) {
        return itemsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateList(List<Item> _list) {
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

        holder.setData(BitmapCreator.getDrawable(getItem(position).getIcon()), getItem(position).getName());
        holder.setListener(position);
        return convertView;
    }

    class ViewHolder {
        private TextView nameItem;
        private ImageView previewImage, deleteButton, sendButton;

        public void initHolder(View _view) {
            this.previewImage = (ImageView) _view.findViewById(R.id.ivPrevProduct);
            this.nameItem = (TextView) _view.findViewById(R.id.tv_title_goods_IS);
            this.deleteButton = (ImageView) _view.findViewById(R.id.iv_delete_shop_item_IS);
            this.sendButton = (ImageView) _view.findViewById(R.id.iv_send_shop_item_IS);
        }

        public void setListener(int position) {
            sendButton.setOnClickListener(productItemListener(position));
            deleteButton.setOnClickListener(productItemListener(position));
        }

        public void setData(Drawable _icon, String _name) {
            previewImage.setImageDrawable(_icon);
            nameItem.setText(_name);
        }
    }

    private void deleteItem(int pos) {
        ItemDAO itemDAO = new ItemDAO(context);
        itemDAO.deleteItem(getItem(pos));
        List<Item> list = itemDAO.getItems(shop_id);
        updateList(list);
        Toast.makeText(context, R.string.delete_item, Toast.LENGTH_SHORT).show();
    }

    private void sendPDFs(int _pos) {
        PDFSender.sendItemPDF(context, getItem(_pos).getPdf());
    }

    private View.OnClickListener productItemListener(final int pos) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_delete_shop_item_IS:
                        deleteItem(pos);
                        break;
                    case R.id.iv_send_shop_item_IS:
                        sendPDFs(pos);
                        break;
                }
            }
        };
        return listener;
    }

}
