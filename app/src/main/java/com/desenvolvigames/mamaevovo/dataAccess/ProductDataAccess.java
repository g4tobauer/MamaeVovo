package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.Product;

public class ProductDataAccess {
    private static ProductDataAccess mInstance;
    private static Context mContext;

    private ProductDataAccess(){}

    public static ProductDataAccess getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = new ProductDataAccess();
        mContext = context;
        return mInstance;
    }
    public Product Get(Product product)
    {
        DbHelper mDbHelper = new DbHelper(mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {Contracts.ProductEntry._ID, Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION};
        String selection = Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION + " = ?";
        String[] selectionArgs = { product.Description };
        String sortOrder = Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION + " DESC";
        Cursor cursor = db.query(Contracts.ProductEntry.TABLE_NAME, projection, selection, selectionArgs,null,null, sortOrder);

        product = new Product();

        cursor.moveToFirst();
        product.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.ProductEntry._ID));
        product.Description = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION));
        db.close();

        return product;
    }

    public Product Insert(Product product)
    {
        DbHelper mDbHelper = new DbHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION, product.Description);
        long newRowId = db.insert(Contracts.ProductEntry.TABLE_NAME, null, values);
        db.close();
        return null;
    }

    public void Update(Product product)
    {
        DbHelper mDbHelper = new DbHelper(mContext);
    }

    public void Delete(Product product)
    {
        DbHelper mDbHelper = new DbHelper(mContext);
    }
}
