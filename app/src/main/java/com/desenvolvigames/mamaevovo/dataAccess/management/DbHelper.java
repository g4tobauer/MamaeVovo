package com.desenvolvigames.mamaevovo.dataAccess.management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_PRODUCTS = "CREATE TABLE " + Contracts.ProductEntry.TABLE_NAME +
            " (" +
                Contracts.ProductEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                Contracts.ProductEntry.COLUMN_NAME_UNIT + INTEGER_TYPE + COMMA_SEP +
                Contracts.ProductEntry.COLUMN_NAME_USASUBITENS + TEXT_TYPE + COMMA_SEP +
                Contracts.ProductEntry.COLUMN_NAME_PRICE + REAL_TYPE +COMMA_SEP +
                Contracts.ProductEntry.COLUMN_NAME_OBS + TEXT_TYPE +
            " )";
    private static final String SQL_CREATE_SUBITENS = "CREATE TABLE " + Contracts.SubItemEntry.TABLE_NAME +
            " (" +
                Contracts.SubItemEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                Contracts.SubItemEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                Contracts.SubItemEntry.COLUMN_NAME_ACTIVE + TEXT_TYPE +
            " )";
    private static final String SQL_CREATE_SALESORDER = "CREATE TABLE " + Contracts.SalesOrderEntry.TABLE_NAME +
            " (" +
                Contracts.SalesOrderEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                Contracts.SalesOrderEntry.COLUMN_NAME_IDDATE + INTEGER_TYPE +
            " )";
    private static final String SQL_CREATE_SALESORDERITEM = "CREATE TABLE " + Contracts.SalesOrderItemEntry.TABLE_NAME +
            " (" +
                Contracts.SalesOrderItemEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                Contracts.SalesOrderItemEntry.COLUMN_NAME_IDSALESORDER + INTEGER_TYPE + COMMA_SEP +
                Contracts.SalesOrderItemEntry.COLUMN_NAME_IDPRODUCT + INTEGER_TYPE + COMMA_SEP +
                Contracts.SalesOrderItemEntry.COLUMN_NAME_AMOUNT + REAL_TYPE +
            " )";
    private static final String SQL_CREATE_SALESORDERSUBITEMBYITEM = "CREATE TABLE " + Contracts.SalesOrderSubItemByItemEntry.TABLE_NAME +
            " (" +
            Contracts.SalesOrderSubItemByItemEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
            Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDER + INTEGER_TYPE + COMMA_SEP +
            Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDERITEM + INTEGER_TYPE + COMMA_SEP +
            Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSUBITEM + INTEGER_TYPE +
            " )";

    private static final String SQL_DELETE_PRODUCTS = "DROP TABLE IF EXISTS " + Contracts.ProductEntry.TABLE_NAME;
    private static final String SQL_DELETE_MENUS = "DROP TABLE IF EXISTS " + Contracts.SubItemEntry.TABLE_NAME;
    private static final String SQL_DELETE_SALESORDER = "DROP TABLE IF EXISTS " + Contracts.SalesOrderEntry.TABLE_NAME;
    private static final String SQL_DELETE_SALESORDERITEM = "DROP TABLE IF EXISTS " + Contracts.SalesOrderItemEntry.TABLE_NAME;
    private static final String SQL_DELETE_SALESORDERSUBITEMBYITEM = "DROP TABLE IF EXISTS " + Contracts.SalesOrderSubItemByItemEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS);
        db.execSQL(SQL_CREATE_SUBITENS);
        db.execSQL(SQL_CREATE_SALESORDER);
        db.execSQL(SQL_CREATE_SALESORDERITEM);
        db.execSQL(SQL_CREATE_SALESORDERSUBITEMBYITEM);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCTS);
        db.execSQL(SQL_DELETE_MENUS);
        db.execSQL(SQL_DELETE_SALESORDER);
        db.execSQL(SQL_DELETE_SALESORDERITEM);
        db.execSQL(SQL_DELETE_SALESORDERSUBITEMBYITEM);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}