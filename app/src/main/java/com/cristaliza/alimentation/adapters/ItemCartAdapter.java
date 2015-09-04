package com.cristaliza.alimentation.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.orm_database.DBManager;
import com.cristaliza.alimentation.orm_database.DBProduct;
import com.cristaliza.alimentation.untils.BitmapCreator;

import java.util.List;

/**
 * Created by Feltsan on 15.05.2015.
 */
public class ItemCartAdapter extends BaseAdapter {
    private Context context;
    private List<DBProduct> itemsData;
    private LayoutInflater inflater;
    private long mShopId;


    public ItemCartAdapter(Context _context, List<DBProduct> _data, long _shopId) {
        if (_context != null) {
            context = _context;
            itemsData = _data;
            mShopId = _shopId;

            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    @Override
    public int getCount() {
        return itemsData.size();
    }

    @Override
    public DBProduct getItem(int position) {
        return itemsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateList(List<DBProduct> _list) {
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

        holder.setData(BitmapCreator.getDrawable(getItem(position).getImage()), getItem(position).getName());
        holder.setListener(position);
        return convertView;
    }

    class ViewHolder {
        private TextView nameItem;
        private ImageView previewImage, deleteButton;

        public void initHolder(View _view) {
            this.previewImage  = (ImageView) _view.findViewById(R.id.ivPrevProduct);
            this.nameItem      = (TextView) _view.findViewById(R.id.tv_title_goods_IS);
            this.deleteButton  = (ImageView) _view.findViewById(R.id.iv_delete_shop_product_IS);
        }

        public void setListener(int position) {
            deleteButton.setOnClickListener(productItemListener(position));
        }

        public void setData(Drawable _icon, String _name) {
            previewImage.setImageDrawable(_icon);
            nameItem.setText(_name);
        }
    }

    private void deleteItem(int pos) {
        DBManager.deleteProduct(itemsData.get(pos).getId());
        List<DBProduct> products = DBManager.getProducts(mShopId);
        updateList(products);
        Toast.makeText(context, R.string.delete_item, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener productItemListener(final int pos) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_delete_shop_product_IS:
                        deleteItem(pos);
                        break;
                }
            }
        };
    }

}
