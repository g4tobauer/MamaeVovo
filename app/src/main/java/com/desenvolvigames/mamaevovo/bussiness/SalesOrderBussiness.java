package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderDataAccess;
import com.desenvolvigames.mamaevovo.entities.SalesOrder;

import java.util.ArrayList;

public class SalesOrderBussiness {

    private static SalesOrderBussiness mInstance;
    private Context mContext;

    private SalesOrderBussiness(){}

    public static SalesOrderBussiness getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = new SalesOrderBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<SalesOrder> Get(SalesOrder subItem)
    {
        return SalesOrderDataAccess.getInstance(mContext).Get(subItem);
    }

    public SalesOrder Insert(SalesOrder subItem)
    {
        return SalesOrderDataAccess.getInstance(mContext).Insert(subItem);
    }

    public boolean Update(SalesOrder subItem)
    {
        return SalesOrderDataAccess.getInstance(mContext).Update(subItem);
    }

    public boolean Delete(SalesOrder subItem)
    {
        return SalesOrderDataAccess.getInstance(mContext).Delete(subItem);
    }
}
