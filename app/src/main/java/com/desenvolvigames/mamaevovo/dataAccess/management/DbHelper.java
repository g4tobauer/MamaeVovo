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
                Contracts.ProductEntry.COLUMN_NAME_PRICE + REAL_TYPE +COMMA_SEP +
                Contracts.ProductEntry.COLUMN_NAME_OBS + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_PRODUCTS = "DROP TABLE IF EXISTS " + Contracts.ProductEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCTS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}