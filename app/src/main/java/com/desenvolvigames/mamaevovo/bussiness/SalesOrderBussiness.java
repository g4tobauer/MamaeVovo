package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderDataAccess;
import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderItemDataAccess;
import com.desenvolvigames.mamaevovo.entities.SalesOrder;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;

import java.util.ArrayList;
import java.util.List;

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
            ArrayList<SalesOrderItem> lstSalesOrderItem = SalesOrderItemBussiness.getInstance(mContext).Get(salesOrderItem);
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
        if(result)
        {
            SalesOrderItem salesOrderItem = new SalesOrderItem();
            salesOrderItem.IdSalesOrder = salesOrder.Id;
            List<SalesOrderItem> lstSalesOrderItem = SalesOrderItemBussiness.getInstance(mContext).Get(salesOrderItem);
            for (SalesOrderItem salesOrderItemTemp : salesOrder.SalesOrderItem)
            {
                if(salesOrderItemTemp.Id == null)
                {
                    salesOrderItemTemp.IdSalesOrder = salesOrder.Id;
                    if(SalesOrderItemBussiness.getInstance(mContext).Insert(salesOrderItemTemp) == null)
                    {
                        result = false;
                        break;
                    }
                }
                else
                {
                    if (SalesOrderItemBussiness.getInstance(mContext).Update(salesOrderItemTemp))
                    {
                        SalesOrderItem salesOrderItemDel = null;
                        for(SalesOrderItem salesOrderItemDelTemp : lstSalesOrderItem)
                        {
                            if(salesOrderItemTemp.Id == salesOrderItemDelTemp.Id)
                            {
                                salesOrderItemDel = salesOrderItemDelTemp;
                                break;
                            }
                        }
                        lstSalesOrderItem.remove(salesOrderItemDel);
                    }else
                    {
                        result = false;
                        break;
                    }
                }
            }
            if(result)
            {
                for (SalesOrderItem salesOrderItemDelTemp : lstSalesOrderItem) {
                    if (!SalesOrderItemBussiness.getInstance(mContext).Delete(salesOrderItemDelTemp)) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public boolean Delete(SalesOrder salesOrder){
        boolean result = true;

        SalesOrder salesOrderTemp = new SalesOrder();
        salesOrderTemp.Id = salesOrder.Id;
        salesOrderTemp = Get(salesOrderTemp).get(0);
        for(SalesOrderItem salesOrderItemTemp : salesOrderTemp.SalesOrderItem)
        {
            result = SalesOrderItemBussiness.getInstance(mContext).Delete(salesOrderItemTemp);
        }
        if(result) {
            result = SalesOrderDataAccess.getInstance(mContext).Delete(salesOrder);
        }
        return result;
    }
}
