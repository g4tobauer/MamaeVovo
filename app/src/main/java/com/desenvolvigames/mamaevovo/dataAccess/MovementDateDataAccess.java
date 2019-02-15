package com.desenvolvigames.mamaevovo.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desenvolvigames.mamaevovo.dataAccess.management.Contracts;
import com.desenvolvigames.mamaevovo.dataAccess.management.DbHelper;
import com.desenvolvigames.mamaevovo.entities.MovementDate;
import com.desenvolvigames.mamaevovo.helpers.DateHelper;

import java.util.ArrayList;

public class MovementDateDataAccess {
    private static MovementDateDataAccess mInstance;
    private DbHelper mDbHelper;

    private MovementDateDataAccess(){}

    public static MovementDateDataAccess getInstance(Context context){
        if(mInstance==null)
            mInstance = new MovementDateDataAccess();
        mInstance.CreateHelper(context);
        return mInstance;
    }

    private void CreateHelper(Context context)
    {
        mDbHelper = new DbHelper(context);
    }

    public ArrayList<MovementDate> Get(MovementDate movementDate){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        StringBuilder sbSelection = new StringBuilder();
        ArrayList<String> arSelectionArgs = new ArrayList<>();

        if(!(movementDate.Id == null))
        {
            sbSelection.append(Contracts.MovementDateEntry._ID + " = ?");
            arSelectionArgs.add(movementDate.Id.toString());
        }
        if(!(movementDate.Date == null))
        {
            if(sbSelection.length() > 0) sbSelection.append(" AND ");
            sbSelection.append(Contracts.MovementDateEntry.COLUMN_NAME_DATE + " = ?");
            arSelectionArgs.add(movementDate.Date.toString());
        }

        String sortOrder = Contracts.MovementDateEntry._ID + " ASC";

        String[] projection = {
                Contracts.MovementDateEntry._ID,
                Contracts.MovementDateEntry.COLUMN_NAME_DATE
        };
        String[] selectionArgs = arSelectionArgs.toArray(new String[0]);
        Cursor cursor = db.query(Contracts.SubItemEntry.TABLE_NAME, projection, sbSelection.length() > 0 ? sbSelection.toString() : null, selectionArgs,null,null, sortOrder);

        ArrayList<MovementDate> mArrayList = new ArrayList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            MovementDate newMovementDate = new MovementDate();
            newMovementDate.Id = cursor.getLong(cursor.getColumnIndexOrThrow(Contracts.MovementDateEntry._ID));
            newMovementDate.Date = DateHelper.convertStringToDate(cursor.getString(cursor.getColumnIndexOrThrow(Contracts.MovementDateEntry.COLUMN_NAME_DATE)));
            mArrayList.add(newMovementDate);
        }
        db.close();

        return mArrayList;
    }

    public MovementDate Insert(MovementDate movementDate){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(movementDate.Date != null)
            values.put(Contracts.MovementDateEntry.COLUMN_NAME_DATE, DateHelper.convertDateToString(movementDate.Date));

        long newRowId = db.insert(Contracts.MovementDateEntry.TABLE_NAME, null, values);
        db.close();

        MovementDate result = null;
        if(!(newRowId < 0))
        {
            result = new MovementDate();
            result.Id = newRowId;
            result = Get(result).get(0);
        }
        return result;
    }

    public boolean Update(MovementDate movementDate){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(movementDate.Date != null)
            values.put(Contracts.MovementDateEntry.COLUMN_NAME_DATE, DateHelper.convertDateToString(movementDate.Date));

        String selection = Contracts.MovementDateEntry._ID + " = ?";
        String[] selectionArgs = { movementDate.Id.toString() };
        int count = db.update(
                Contracts.MovementDateEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return (!(count == 0));
    }

    public boolean Delete(MovementDate movementDate){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] selectionArgs = new String[1];
        String selection = null;
        if(!(movementDate.Id == null))
        {
            selection = Contracts.MovementDateEntry._ID + " = ?";
            selectionArgs[0] = movementDate.Id.toString();
        }
        boolean result = db.delete(Contracts.MovementDateEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();
        return result;
    }
}
