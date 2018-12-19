package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.SubItem;

import java.util.ArrayList;

public class SubItemDataAccess {

    private static SubItemDataAccess mInstance;
    private DbHelper mDbHelper;

    private SubItemDataAccess(){}

    public static SubItemDataAccess getInstance(Context context){
        if(mInstance==null)
            mInstance = new SubItemDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<SubItem> Get(SubItem subItem){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(subItem.Id == null))
        {
            sbSelection.append(Contracts.SubItemEntry._ID + " = ?");
            arSelectionArgs.add(subItem.Id.toString());
        }
        if(!(subItem.Description == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.SubItemEntry.COLUMN_NAME_DESCRIPTION + " = ?");
            arSelectionArgs.add(subItem.Description);
        }
        if(!(subItem.Active == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.SubItemEntry.COLUMN_NAME_ACTIVE + " = ?");
            arSelectionArgs.add(subItem.Active.toString());
        }

        String sortOrder = Contracts.SubItemEntry._ID + " ASC";

        String[] projection = {
                Contracts.SubItemEntry._ID,
                Contracts.SubItemEntry.COLUMN_NAME_DESCRIPTION,
                Contracts.SubItemEntry.COLUMN_NAME_ACTIVE
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.SubItemEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<SubItem> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            SubItem newProd = new SubItem();
            newProd.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.SubItemEntry._ID));
            newProd.Description = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.SubItemEntry.COLUMN_NAME_DESCRIPTION));
            newProd.Active = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(Contracts.SubItemEntry.COLUMN_NAME_ACTIVE)));
            mArrayList.add(newProd);
        }
        db.close();

        return mArrayList;
    }

    public SubItem Insert(SubItem subItem){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(subItem.Description != null)
            values.put(Contracts.SubItemEntry.COLUMN_NAME_DESCRIPTION, subItem.Description);
        if(subItem.Active != null)
            values.put(Contracts.SubItemEntry.COLUMN_NAME_ACTIVE, subItem.Active.toString());

        long newRowId = db.insert(Contracts.SubItemEntry.TABLE_NAME, null, values);
        db.close();

        SubItem result = null;
        if(!(newRowId < 0))
        {
            result = new SubItem();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(SubItem subItem){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(subItem.Description != null)
            values.put(Contracts.SubItemEntry.COLUMN_NAME_DESCRIPTION, subItem.Description);
        if(subItem.Active != null)
            values.put(Contracts.SubItemEntry.COLUMN_NAME_ACTIVE, subItem.Active.toString());

        String selection = Contracts.SubItemEntry._ID + " = ?";
        String[] selectionArgs = { subItem.Id.toString() };
        int count = db.update(
                Contracts.SubItemEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(SubItem subItem){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(subItem.Id == null))
        {
            selection = Contracts.SubItemEntry._ID + " = ?";
            selectionArgs[0] = subItem.Id.toString();
        }
        boolean result = db.delete(Contracts.SubItemEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
