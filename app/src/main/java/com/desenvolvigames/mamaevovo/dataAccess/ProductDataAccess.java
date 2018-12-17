package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

import java.util.ArrayList;

public class ProductDataAccess {
    private static ProductDataAccess mInstance;
    private DbHelper mDbHelper;

    private ProductDataAccess(){}

    public static ProductDataAccess getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = new ProductDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<Product> Get(Product product)
    {
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
        if(!(product.Unit == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.ProductEntry.COLUMN_NAME_UNIT + " = ?");
            arSelectionArgs.add(ProductUnitEnum.toInteger(product.Unit).toString());
        }
        if(!(product.UsaSubItens == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.ProductEntry.COLUMN_NAME_USASUBITENS + " = ?");
            arSelectionArgs.add(product.UsaSubItens.toString());
        }
        if(!(product.Price == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.ProductEntry.COLUMN_NAME_PRICE + " = ?");
            arSelectionArgs.add(product.Price.toString());
        }
        if(!(product.Obs == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.ProductEntry.COLUMN_NAME_OBS + " = ?");
            arSelectionArgs.add(product.Obs);
        }
        String sortOrder = Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION + " ASC";

        String[] projection = {
                Contracts.ProductEntry._ID,
                Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION,
                Contracts.ProductEntry.COLUMN_NAME_UNIT,
                Contracts.ProductEntry.COLUMN_NAME_USASUBITENS,
                Contracts.ProductEntry.COLUMN_NAME_PRICE,
                Contracts.ProductEntry.COLUMN_NAME_OBS
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.ProductEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<Product> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            Product newProd = new Product();
            newProd.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.ProductEntry._ID));
            newProd.Description = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION));
            newProd.Unit = ProductUnitEnum.fromInteger(cursor.getInt(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_UNIT)));
            newProd.UsaSubItens = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_USASUBITENS)));
            newProd.Price = cursor.getDouble(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_PRICE));
            newProd.Obs = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.ProductEntry.COLUMN_NAME_OBS));
            mArrayList.add(newProd);
        }
        db.close();

        return mArrayList;
    }

    public Product Insert(Product product)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(product.Description != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION, product.Description);
        if(product.Unit != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_UNIT, ProductUnitEnum.toInteger(product.Unit));
        if(product.UsaSubItens != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_USASUBITENS, product.UsaSubItens.toString());
        if(product.Price != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_PRICE, product.Price);
        if(product.Obs != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_OBS, product.Obs);

        long newRowId = db.insert(Contracts.ProductEntry.TABLE_NAME, null, values);
        db.close();

        Product result = null;
        if(!(newRowId < 0))
        {
            result = new Product();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(Product product)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(product.Description != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_DESCRIPTION, product.Description);
        if(product.Unit != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_UNIT, ProductUnitEnum.toInteger(product.Unit));
        if(product.UsaSubItens != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_USASUBITENS, product.UsaSubItens.toString());
        if(product.Price != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_PRICE, product.Price);
        if(product.Obs != null)
            values.put(Contracts.ProductEntry.COLUMN_NAME_OBS, product.Obs);

        String selection = Contracts.ProductEntry._ID + " = ?";
        String[] selectionArgs = { product.Id.toString() };
        int count = db.update(
                Contracts.ProductEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(Product product)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(product.Id == null))
        {
            selection = Contracts.ProductEntry._ID + " = ?";
            selectionArgs[0] = product.Id.toString();
        }
        boolean result = db.delete(Contracts.ProductEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
