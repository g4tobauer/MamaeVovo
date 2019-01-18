package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderSubItemByItemDataAccess;
import com.desenvolvigames.mamaevovo.entities.SalesOrderSubItemByItem;

import java.util.ArrayList;

public class SalesOrderSubItemByItemBussiness {

    private static SalesOrderSubItemByItemBussiness mInstance;
    private Context mContext;

    private SalesOrderSubItemByItemBussiness(){}

    public static SalesOrderSubItemByItemBussiness getInstance(Context context){
        if(mInstance==null)
            mInstance = new SalesOrderSubItemByItemBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context){mContext = context;}

    public ArrayList<SalesOrderSubItemByItem> Get(SalesOrderSubItemByItem salesOrderSubItemByItem){
        return SalesOrderSubItemByItemDataAccess.getInstance(mContext).Get(salesOrderSubItemByItem);
    }

    public SalesOrderSubItemByItem Insert(SalesOrderSubItemByItem salesOrderSubItemByItem){
        return SalesOrderSubItemByItemDataAccess.getInstance(mContext).Insert(salesOrderSubItemByItem);
    }

    public boolean Update(SalesOrderSubItemByItem salesOrderSubItemByItem){
        return SalesOrderSubItemByItemDataAccess.getInstance(mContext).Update(salesOrderSubItemByItem);
    }

    public boolean Delete(SalesOrderSubItemByItem salesOrderSubItemByItem){
        return SalesOrderSubItemByItemDataAccess.getInstance(mContext).Delete(salesOrderSubItemByItem);
    }
}
