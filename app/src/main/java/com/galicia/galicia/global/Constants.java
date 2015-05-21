package com.galicia.galicia.global;

/**
 * Created by Bogdan on 14.05.2015.
 */
public abstract class Constants {

    public static final String UPDATE_TIME              = "update_time";
    public static final String ITEM_SERIAZ              = "item_seriaz";
    public static final String FICHA_CATA               = "ficha_cata";
    public static final String POSITION                 = "position";
    public static final String PRODUCT_LIST             = "product_list";
    public static final String MIME_TYPE                = "text/html";
    public static final String ENCODING                 = "UTF-8";
    public static final String PARAM_OPEN               = "OPEN";

//<----------------------------------DB------------------------------------------------->

    public static final String TABLE_SHOP               = "shop";
    public static final String ID_COLUMN                = "id";
    public static final String NAME_COLUMN              = "name";

    public static final String TABLE_ITEM               = "item";
    public static final String COLUMN_LINK_IMAGE        = "link_image";
    public static final String COLUMN_LINK_PDF          = "link_pdf";
    public static final String COLUMN_ID_SHOP           = "shop_id";

    public static final String DATABASE_NAME            = "shop_items.db";
    public static final int DATABASE_VERSION            = 1;

    public static final String CREATE_SHOP_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SHOP
            + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COLUMN + " TEXT NOT NULL"
            + ");";

    public static final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ITEM +
            " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COLUMN + " TEXT NOT NULL, "
            + COLUMN_LINK_IMAGE + " TEXT NOT NULL, "
            + COLUMN_LINK_PDF + " TEXT NOT NULL, "
            + COLUMN_ID_SHOP + " INT, " + " FOREIGN KEY("
            + COLUMN_ID_SHOP + ") REFERENCES "
            + TABLE_SHOP + "(id) "
            + ");";

    public static final String DELETE_SHOP_TABLE = "DROP TABLE IF EXISTS " + TABLE_SHOP;
    public static final String DELETE_ITEM_TABLE = "DROP TABLE IF EXISTS " + TABLE_ITEM;

    public static final String ITEM_ID_WITH_PREFIX = "i.id";
    public static final String ITEM_NAME_WITH_PREFIX = "i.name";
    public static final String SHOP_NAME_WITH_PREFIX = "s.name";

    public static final String WHERE_ID_EQUALS = ID_COLUMN + " =?";
}
