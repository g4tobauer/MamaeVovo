package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.Menu;

import java.util.ArrayList;

public class MenuDataAccess {

    private static MenuDataAccess mInstance;
    private DbHelper mDbHelper;

    private MenuDataAccess(){}

    public static MenuDataAccess getInstance(Context context){
        if(mInstance==null)
            mInstance = new MenuDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<Menu> Get(Menu menu){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(menu.Id == null))
        {
            sbSelection.append(Contracts.MenuEntry._ID + " = ?");
            arSelectionArgs.add(menu.Id.toString());
        }
        if(!(menu.Description == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.MenuEntry.COLUMN_NAME_DESCRIPTION + " = ?");
            arSelectionArgs.add(menu.Description);
        }
        if(!(menu.Active == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.MenuEntry.COLUMN_NAME_ACTIVE + " = ?");
            arSelectionArgs.add(menu.Active.toString());
        }

        String sortOrder = Contracts.MenuEntry.COLUMN_NAME_DESCRIPTION + " ASC";

        String[] projection = {
                Contracts.MenuEntry._ID,
                Contracts.MenuEntry.COLUMN_NAME_DESCRIPTION,
                Contracts.MenuEntry.COLUMN_NAME_ACTIVE
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.MenuEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<Menu> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            Menu newProd = new Menu();
            newProd.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.MenuEntry._ID));
            newProd.Description = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.MenuEntry.COLUMN_NAME_DESCRIPTION));
            newProd.Active = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(Contracts.MenuEntry.COLUMN_NAME_ACTIVE)));
            mArrayList.add(newProd);
        }
        db.close();

        return mArrayList;
    }

    public Menu Insert(Menu menu){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(menu.Description != null)
            values.put(Contracts.MenuEntry.COLUMN_NAME_DESCRIPTION, menu.Description);
        if(menu.Active != null)
            values.put(Contracts.MenuEntry.COLUMN_NAME_ACTIVE, menu.Active.toString());

        long newRowId = db.insert(Contracts.MenuEntry.TABLE_NAME, null, values);
        db.close();

        Menu result = null;
        if(!(newRowId < 0))
        {
            result = new Menu();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(Menu menu){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(menu.Description != null)
            values.put(Contracts.MenuEntry.COLUMN_NAME_DESCRIPTION, menu.Description);
        if(menu.Active != null)
            values.put(Contracts.MenuEntry.COLUMN_NAME_ACTIVE, menu.Active.toString());

        String selection = Contracts.MenuEntry._ID + " = ?";
        String[] selectionArgs = { menu.Id.toString() };
        int count = db.update(
                Contracts.MenuEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(Menu menu){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(menu.Id == null))
        {
            selection = Contracts.MenuEntry._ID + " = ?";
            selectionArgs[0] = menu.Id.toString();
        }
        boolean result = db.delete(Contracts.MenuEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
