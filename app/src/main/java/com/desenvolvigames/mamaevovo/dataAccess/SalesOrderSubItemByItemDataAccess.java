package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.SalesOrderSubItemByItem;

import java.util.ArrayList;

public class SalesOrderSubItemByItemDataAccess {

    private static SalesOrderSubItemByItemDataAccess mInstance;
    private DbHelper mDbHelper;

    private SalesOrderSubItemByItemDataAccess(){}

    public static SalesOrderSubItemByItemDataAccess getInstance(Context context){
        if(mInstance==null)
            mInstance = new SalesOrderSubItemByItemDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<SalesOrderSubItemByItem> Get(SalesOrderSubItemByItem salesOrderSubItemByItem){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(salesOrderSubItemByItem.Id == null))
        {
            sbSelection.append(Contracts.SalesOrderSubItemByItemEntry._ID + " = ?");
            arSelectionArgs.add(salesOrderSubItemByItem.Id.toString());
        }
        if(!(salesOrderSubItemByItem.IdSalesOrder == null))
        {
            sbSelection.append(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDER + " = ?");
            arSelectionArgs.add(salesOrderSubItemByItem.IdSalesOrder.toString());
        }
        if(!(salesOrderSubItemByItem.IdSalesOrderItem == null))
        {
            sbSelection.append(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDERITEM + " = ?");
            arSelectionArgs.add(salesOrderSubItemByItem.IdSalesOrderItem.toString());
        }
        if(!(salesOrderSubItemByItem.IdSubItem == null))
        {
            sbSelection.append(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSUBITEM + " = ?");
            arSelectionArgs.add(salesOrderSubItemByItem.IdSubItem.toString());
        }

        String sortOrder = Contracts.SalesOrderSubItemByItemEntry._ID + " ASC";

        String[] projection = {
                Contracts.SalesOrderSubItemByItemEntry._ID,
                Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDER,
                Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDERITEM,
                Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSUBITEM
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.SalesOrderSubItemByItemEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<SalesOrderSubItemByItem> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            SalesOrderSubItemByItem newSubItem = new SalesOrderSubItemByItem();
            newSubItem.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderSubItemByItemEntry._ID));
            newSubItem.IdSalesOrder = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDER));
            newSubItem.IdSalesOrderItem = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDERITEM));
            newSubItem.IdSubItem = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSUBITEM));
            mArrayList.add(newSubItem);
        }
        db.close();

        return mArrayList;
    }

    public SalesOrderSubItemByItem Insert(SalesOrderSubItemByItem salesOrderSubItemByItem){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(salesOrderSubItemByItem.IdSalesOrder != null)
            values.put(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDER, salesOrderSubItemByItem.IdSalesOrder);
        if(salesOrderSubItemByItem.IdSalesOrderItem != null)
            values.put(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDERITEM, salesOrderSubItemByItem.IdSalesOrderItem);
        if(salesOrderSubItemByItem.IdSubItem != null)
            values.put(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSUBITEM, salesOrderSubItemByItem.IdSubItem);

        long newRowId = db.insert(Contracts.SalesOrderSubItemByItemEntry.TABLE_NAME, null, values);
        db.close();

        SalesOrderSubItemByItem result = null;
        if(!(newRowId < 0))
        {
            result = new SalesOrderSubItemByItem();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(SalesOrderSubItemByItem salesOrderSubItemByItem){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(salesOrderSubItemByItem.IdSalesOrder != null)
            values.put(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDER, salesOrderSubItemByItem.IdSalesOrder);
        if(salesOrderSubItemByItem.IdSalesOrderItem != null)
            values.put(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSALESORDERITEM, salesOrderSubItemByItem.IdSalesOrderItem);
        if(salesOrderSubItemByItem.IdSubItem != null)
            values.put(Contracts.SalesOrderSubItemByItemEntry.COLUMN_NAME_IDSUBITEM, salesOrderSubItemByItem.IdSubItem);

        String selection = Contracts.SalesOrderSubItemByItemEntry._ID + " = ?";
        String[] selectionArgs = { salesOrderSubItemByItem.Id.toString() };
        int count = db.update(
                Contracts.SalesOrderSubItemByItemEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(SalesOrderSubItemByItem salesOrderSubItemByItem){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(salesOrderSubItemByItem.Id == null))
        {
            selection = Contracts.SalesOrderSubItemByItemEntry._ID + " = ?";
            selectionArgs[0] = salesOrderSubItemByItem.Id.toString();
        }
        boolean result = db.delete(Contracts.SalesOrderSubItemByItemEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
