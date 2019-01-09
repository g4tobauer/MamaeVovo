package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderItemDataAccess;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;

import java.util.ArrayList;

public class SalesOrderItemBussiness {

    private static SalesOrderItemBussiness mInstance;
    private Context mContext;

    private SalesOrderItemBussiness(){}

    public static SalesOrderItemBussiness getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = new SalesOrderItemBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<SalesOrderItem> Get(SalesOrderItem salesOrderItem)
    {
        ArrayList<SalesOrderItem> lstSalesOrderItem = SalesOrderItemDataAccess.getInstance(mContext).Get(salesOrderItem);
        for(SalesOrderItem salesOrderItemTemp : lstSalesOrderItem)
        {
            if(salesOrderItemTemp.Product != null && salesOrderItemTemp.Product.Id != null) {
                salesOrderItemTemp.Product = ProductBussiness.getInstance(mContext).Get(salesOrderItemTemp.Product).get(0);
            }
        }
        return lstSalesOrderItem;
    }

    public SalesOrderItem Insert(SalesOrderItem salesOrderItem)
    {
        return SalesOrderItemDataAccess.getInstance(mContext).Insert(salesOrderItem);
    }

    public boolean Update(SalesOrderItem salesOrderItem)
    {
        return SalesOrderItemDataAccess.getInstance(mContext).Update(salesOrderItem);
    }

    public boolean Delete(SalesOrderItem salesOrderItem)
    {
        return SalesOrderItemDataAccess.getInstance(mContext).Delete(salesOrderItem);
    }
}
