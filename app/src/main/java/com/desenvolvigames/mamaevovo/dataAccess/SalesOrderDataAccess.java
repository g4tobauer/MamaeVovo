package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.SalesOrder;

import java.util.ArrayList;

public class SalesOrderDataAccess {
    private static SalesOrderDataAccess mInstance;
    private DbHelper mDbHelper;

    private SalesOrderDataAccess(){}

    public static SalesOrderDataAccess getInstance(Context context){
        if(mInstance==null)
            mInstance = new SalesOrderDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<SalesOrder> Get(SalesOrder salesOrder){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(salesOrder.Id == null))
        {
            sbSelection.append(Contracts.SalesOrderEntry._ID + " = ?");
            arSelectionArgs.add(salesOrder.Id.toString());
        }
        String sortOrder = Contracts.SalesOrderEntry._ID + " ASC";

        String[] projection = {
                Contracts.SalesOrderEntry._ID,
                Contracts.SalesOrderEntry.COLUMN_NAME_IDDATE
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.SalesOrderEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<SalesOrder> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            SalesOrder newProd = new SalesOrder();
            newProd.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderEntry._ID));
            newProd.IdDate = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SalesOrderEntry.COLUMN_NAME_IDDATE));
            mArrayList.add(newProd);
        }
        db.close();

        return mArrayList;
    }

    public SalesOrder Insert(SalesOrder salesOrder){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(salesOrder.IdDate != null)
            values.put(Contracts.SalesOrderEntry.COLUMN_NAME_IDDATE, salesOrder.IdDate);

        long newRowId = db.insert(Contracts.SalesOrderEntry.TABLE_NAME, null, values);
        db.close();

        SalesOrder result = null;
        if(!(newRowId < 0))
        {
            result = new SalesOrder();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(SalesOrder salesOrder){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(salesOrder.IdDate != null)
            values.put(Contracts.SalesOrderEntry.COLUMN_NAME_IDDATE, salesOrder.IdDate);

        String selection = Contracts.SalesOrderEntry._ID + " = ?";
        String[] selectionArgs = { salesOrder.Id.toString() };
        int count = db.update(
                Contracts.SalesOrderEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(SalesOrder salesOrder){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(salesOrder.Id == null))
        {
            selection = Contracts.SalesOrderEntry._ID + " = ?";
            selectionArgs[0] = salesOrder.Id.toString();
        }
        boolean result = db.delete(Contracts.SalesOrderEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
