package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderDataAccess;
import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderItemDataAccess;
import com.desenvolvigames.mamaevovo.entities.SalesOrder;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;

import java.util.ArrayList;

public class SalesOrderBussiness {

    private static SalesOrderBussiness mInstance;
    private Context mContext;

    private SalesOrderBussiness(){}

    public static SalesOrderBussiness getInstance(Context context){
        if(mInstance==null)
            mInstance = new SalesOrderBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<SalesOrder> Get(SalesOrder salesOrder){
        ArrayList<SalesOrder> lstSalesOrder = SalesOrderDataAccess.getInstance(mContext).Get(salesOrder);
        for(SalesOrder salesOrderTemp : lstSalesOrder)
        {
            SalesOrderItem salesOrderItem = new SalesOrderItem();
            salesOrderItem.IdSalesOrder = salesOrderTemp.Id;
            ArrayList<SalesOrderItem> lstSalesOrderItem = SalesOrderItemDataAccess.getInstance(mContext).Get(salesOrderItem);
            salesOrderTemp.SalesOrderItem = lstSalesOrderItem;
        }
        return lstSalesOrder;
    }

    public SalesOrder Insert(SalesOrder salesOrder){
        ArrayList<SalesOrderItem> salesOrderItem = salesOrder.SalesOrderItem;
        salesOrder = SalesOrderDataAccess.getInstance(mContext).Insert(salesOrder);
        salesOrder.SalesOrderItem = new ArrayList();
        for(SalesOrderItem salesOrderItemTemp : salesOrderItem)
        {
            salesOrderItemTemp.IdSalesOrder = salesOrder.Id;
            salesOrder.SalesOrderItem.add(SalesOrderItemBussiness.getInstance(mContext).Insert(salesOrderItemTemp));
        }
        return salesOrder;
    }

    public boolean Update(SalesOrder salesOrder){
        boolean result;
        result = SalesOrderDataAccess.getInstance(mContext).Update(salesOrder);
        if(result) {
            for (SalesOrderItem salesOrderItemTemp : salesOrder.SalesOrderItem) {
                if (!SalesOrderItemBussiness.getInstance(mContext).Update(salesOrderItemTemp)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public boolean Delete(SalesOrder salesOrder){
        boolean result = false;
        for(SalesOrderItem salesOrderItemTemp : salesOrder.SalesOrderItem)
        {
            result = SalesOrderItemBussiness.getInstance(mContext).Update(salesOrderItemTemp);
        }
        if(result) {
            result = SalesOrderDataAccess.getInstance(mContext).Delete(salesOrder);
        }
        return result;
    }
}
