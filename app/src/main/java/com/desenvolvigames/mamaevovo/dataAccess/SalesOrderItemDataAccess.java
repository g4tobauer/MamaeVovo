package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;

import java.util.ArrayList;

public class SalesOrderItemDataAccess {
    private static SalesOrderItemDataAccess mInstance;
    private DbHelper mDbHelper;

    private SalesOrderItemDataAccess(){}

    public static SalesOrderItemDataAccess getInstance(Context context){
        if(mInstance==null)
            mInstance = new SalesOrderItemDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<SalesOrderItem> Get(SalesOrderItem salesOrder){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(salesOrder.Id == null))
        {
            sbSelection.append(Contracts.SalesOrderItemEntry._ID + " = ?");
            arSelectionArgs.add(salesOrder.Id.toString());
        }
        if(!(salesOrder.IdSalesOrder == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDSALESORDER + " = ?");
            arSelectionArgs.add(salesOrder.IdSalesOrder.toString());
        }
        if(!(salesOrder.Product == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDPRODUCT + " = ?");
            arSelectionArgs.add(salesOrder.Product.Id.toString());
        }
        if(!(salesOrder.Quantidade == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.SalesOrderItemEntry.COLUMN_NAME_AMOUNT + " = ?");
            arSelectionArgs.add(salesOrder.Quantidade.toString());
        }

        String sortOrder = Contracts.SalesOrderItemEntry._ID + " ASC";

        String[] projection = {
                Contracts.SalesOrderItemEntry._ID,
                Contracts.SalesOrderItemEntry.COLUMN_NAME_IDSALESORDER,
                Contracts.SalesOrderItemEntry.COLUMN_NAME_IDPRODUCT,
                Contracts.SalesOrderItemEntry.COLUMN_NAME_AMOUNT
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.SalesOrderItemEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<SalesOrderItem> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            SalesOrderItem newProd = new SalesOrderItem();
            newProd.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderItemEntry._ID));
            newProd.IdSalesOrder = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDSALESORDER));
            newProd.Quantidade = cursor.getDouble(cursor.getColumnIndexOrThrow(Contracts.SalesOrderItemEntry.COLUMN_NAME_AMOUNT));
            newProd.Product = new Product(cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDPRODUCT)));
            mArrayList.add(newProd);
        }
        db.close();

        return mArrayList;
    }

    public SalesOrderItem Insert(SalesOrderItem salesOrder){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(salesOrder.IdSalesOrder != null)
            values.put(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDSALESORDER, salesOrder.IdSalesOrder);
        if(salesOrder.Product != null)
            values.put(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDPRODUCT, salesOrder.Product.Id);
        if(salesOrder.Quantidade != null)
            values.put(Contracts.SalesOrderItemEntry.COLUMN_NAME_AMOUNT, salesOrder.Quantidade);

        long newRowId = db.insert(Contracts.SalesOrderItemEntry.TABLE_NAME, null, values);
        db.close();

        SalesOrderItem result = null;
        if(!(newRowId < 0))
        {
            result = new SalesOrderItem();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(SalesOrderItem salesOrder){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(salesOrder.IdSalesOrder != null)
            values.put(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDSALESORDER, salesOrder.IdSalesOrder);
        if(salesOrder.Product != null)
            values.put(Contracts.SalesOrderItemEntry.COLUMN_NAME_IDPRODUCT, salesOrder.Product.Id);
        if(salesOrder.Quantidade != null)
            values.put(Contracts.SalesOrderItemEntry.COLUMN_NAME_AMOUNT, salesOrder.Quantidade);

        String selection = Contracts.SalesOrderItemEntry._ID + " = ?";
        String[] selectionArgs = { salesOrder.Id.toString() };
        int count = db.update(
                Contracts.SalesOrderItemEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(SalesOrderItem salesOrder){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(salesOrder.Id == null))
        {
            selection = Contracts.SalesOrderItemEntry._ID + " = ?";
            selectionArgs[0] = salesOrder.Id.toString();
        }
        boolean result = db.delete(Contracts.SalesOrderItemEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
