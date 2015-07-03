package com.cristaliza.nacional.orm_database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasia on 26.05.2015.
 */
public  class DBManager {

    //  SHOP

    public static final List<Shop> getShops(){
        return Shop.listAll(Shop.class);
    }

    public static final Shop addShop(final String _shopName){
        List<Shop> shops = Shop.listAll(Shop.class);
        for(Shop tShop : shops){
            if(tShop.getName().equals(_shopName)){
                return tShop;
            }
        }
        final Shop shop = new Shop(_shopName);
        shop.save();
        return shop;
    }

    public static final void deleteShop(final Shop _shop){
        if (_shop.getId() == null)
            return;
        final List<DBItem> items = getItems(_shop.getId());
        for (int i = 0; i < items.size(); i ++){
            deleteItem(items.get(i).getId());
        }
        _shop.delete();
    }

    public static final void deleteAllShop(){
        Shop.deleteAll(Shop.class);
        DBItem.deleteAll(DBItem.class);
        DBProduct.deleteAll(DBItem.class);
    }

    // ITEM

    public static final void addItem(final String _pdf, final Shop _shop, String _name, final String _icon){
        if (_icon == null)
            return;
        final List<DBItem> item = DBItem.find(
                DBItem.class,
                "pdf = ? and shop = ?",
                new String[]{_pdf, Long.toString(_shop.getId())}
        );
        if (item.size() != 0)
            return;

        final DBItem dbItem = new DBItem(_pdf, _shop);
        dbItem.save();
        addProduct(_icon,_name, dbItem);
    }

    public static final void deleteAllItems(final long _shopId){
        final List<DBItem> items = getItems(_shopId);
        for (int i = 0; i < items.size(); i ++){
            deleteItem(items.get(i).getId());
        }
    }

    public   static final List<DBItem> getItems(final long _shopId){
        final String id = Long.toString(_shopId);
        return DBItem.find(DBItem.class, "shop = ?", new String[]{id});
    }

    private static final void deleteItem(final long _itemId){
        final DBItem dbItem = DBItem.findById(DBItem.class, _itemId);
        final List<DBProduct> products = getItemProduct(_itemId);
        for (int i = 0; i < products.size(); i ++){
            deleteProduct(products.get(i).getId());
        }
        dbItem.delete();
    }

    // PRODUCT

    public static final void addProduct(final String _icon, String _name, final DBItem _item){
//        for (int i = 0; i < products.size(); i ++){
            final DBProduct product = new DBProduct(
                    _name,
                    _icon,
                    _item
            );
            product.save();
//        }
    }

    public static final List<DBProduct> getProducts(final long _shopId){
        final List<DBProduct> products = new ArrayList<>();
        final List<DBItem> items = getItems(_shopId);
        for (int i = 0; i < items.size(); i ++){
            final String id = Long.toString(items.get(i).getId());
            products.addAll(DBProduct.find(DBProduct.class, "item=?", new String[]{id}));
        }
        return products;
    }

    public static final void deleteProduct(final long _productId){
        final DBProduct product = DBProduct.findById(DBProduct.class, _productId);
        final DBItem item = product.getItem();
        product.delete();
        if (getItemProduct(item.getId()).size() == 0) {
            item.delete();
        }
    }

    public static final List<DBProduct> getItemProduct(final long _itemId){
        final String id = Long.toString(_itemId);
        return DBProduct.find(DBProduct.class, "item=?", new String[]{id});
    }
}
