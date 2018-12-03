package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.Product;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Product> Get(Product product)
    {
        DbHelper mDbHelper = new DbHelper(mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(product.Id == null))
        {
            sbSelection.append(Contracts.ProductEntry._ID + " = ?");
            arSelectionArgs.add(product.Id.toString());
        }
        if(!(product.Description == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION + " = ?");
            arSelectionArgs.add(product.Description);
        }
        String sortOrder = Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION + " DESC";

        String[] projection = {
                Contracts.ProductEntry._ID,
                Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.ProductEntry.TABLE_NAME, projection, sbSelection.toString(), selectionArgs,null,null, sortOrder);

        ArrayList<Product> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            Product newProd = new Product();
            newProd.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.ProductEntry._ID));
            newProd.Description = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION));
            mArrayList.add(newProd);
        }
        db.close();

        return mArrayList;
    }

    public Product Insert(Product product)
    {
        DbHelper mDbHelper = new DbHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION, product.Description);
        long newRowId = db.insert(Contracts.ProductEntry.TABLE_NAME, null, values);
        db.close();

        Product prod = null;
        if(!(newRowId < 0))
        {
            prod = new Product();
            prod.Id = newRowId;
            prod = Get(prod).get(0);
        }
        return prod;
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
