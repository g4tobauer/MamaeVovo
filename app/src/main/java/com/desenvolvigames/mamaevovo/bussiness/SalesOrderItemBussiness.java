package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SalesOrderItemDataAccess;
import com.desenvolvigames.mamaevovo.entities.Product;
import com.desenvolvigames.mamaevovo.entities.SalesOrderItem;
import com.desenvolvigames.mamaevovo.entities.SalesOrderSubItemByItem;
import com.desenvolvigames.mamaevovo.entities.SubItem;

import java.util.ArrayList;
import java.util.List;

public class SalesOrderItemBussiness {

    private static SalesOrderItemBussiness mInstance;
    private Context mContext;

    private SalesOrderItemBussiness(){}

    public static SalesOrderItemBussiness getInstance(Context context){
        if(mInstance==null)
            mInstance = new SalesOrderItemBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<SalesOrderItem> Get(SalesOrderItem salesOrderItem){
        ArrayList<SalesOrderItem> lstSalesOrderItem = SalesOrderItemDataAccess.getInstance(mContext).Get(salesOrderItem);
        for(SalesOrderItem salesOrderItemTemp : lstSalesOrderItem)
        {
            if(salesOrderItemTemp.Product != null && salesOrderItemTemp.Product.Id != null) {
                salesOrderItemTemp.Product = ProductBussiness.getInstance(mContext).Get(salesOrderItemTemp.Product).get(0);
                SalesOrderSubItemByItem salesOrderSubItemByItem = new SalesOrderSubItemByItem();
                salesOrderSubItemByItem.IdSalesOrder = salesOrderItemTemp.IdSalesOrder;
                salesOrderSubItemByItem.IdSalesOrderItem = salesOrderItemTemp.Id;
                ArrayList<SalesOrderSubItemByItem> lstSalesOrderSubItemByItem = SalesOrderSubItemByItemBussiness.getInstance(mContext).Get(salesOrderSubItemByItem);
                if(lstSalesOrderSubItemByItem != null)
                {
                    salesOrderItemTemp.SubItemItem = new ArrayList<>();
                    for (SalesOrderSubItemByItem salesOrderSubItemByItemTemp : lstSalesOrderSubItemByItem)
                    {
                        SubItem subItem = new SubItem();
                        subItem.Id = salesOrderSubItemByItemTemp.IdSubItem;
                        subItem = SubItemBussiness.getInstance(mContext).Get(subItem).get(0);
                        salesOrderItemTemp.SubItemItem.add(subItem);
                    }
                }
            }
        }
        return lstSalesOrderItem;
    }

    public SalesOrderItem Insert(SalesOrderItem salesOrderItem){
        SalesOrderItem newSalesOrderItem = SalesOrderItemDataAccess.getInstance(mContext).Insert(salesOrderItem);
        if(newSalesOrderItem != null) {
            for(SubItem subItem : salesOrderItem.SubItemItem) {
                if(subItem.Active) {
                    SalesOrderSubItemByItem salesOrderSubItemByItem = new SalesOrderSubItemByItem();
                    salesOrderSubItemByItem.IdSalesOrder = newSalesOrderItem.IdSalesOrder;
                    salesOrderSubItemByItem.IdSalesOrderItem = newSalesOrderItem.Id;
                    salesOrderSubItemByItem.IdSubItem = subItem.Id;
                    SalesOrderSubItemByItemBussiness.getInstance(mContext).Insert(salesOrderSubItemByItem);
                }
            }
        }
        return newSalesOrderItem;
    }

    public boolean Update(SalesOrderItem salesOrderItem){
        boolean result;
        result = SalesOrderItemDataAccess.getInstance(mContext).Update(salesOrderItem);
        if(result)
        {
            SalesOrderSubItemByItem salesOrderSubItemByItem = new SalesOrderSubItemByItem();
            salesOrderSubItemByItem.IdSalesOrder = salesOrderItem.IdSalesOrder;
            salesOrderSubItemByItem.IdSalesOrderItem = salesOrderItem.Id;

            ArrayList<SubItem> lstSubItemTemp = new ArrayList<>();
            lstSubItemTemp.addAll(salesOrderItem.SubItemItem);

            List<SalesOrderSubItemByItem> lstSalesOrderSubItemByItem = SalesOrderSubItemByItemBussiness.getInstance(mContext).Get(salesOrderSubItemByItem);
            for (SubItem subItemTemp : salesOrderItem.SubItemItem) {
                for (SalesOrderSubItemByItem salesOrderSubItemByItemTemp : lstSalesOrderSubItemByItem)
                {
                    if (subItemTemp.Id == salesOrderSubItemByItemTemp.IdSubItem) {
                        lstSubItemTemp.remove(subItemTemp);
                        break;
                    }
                }
            }
            for(SubItem subItemTemp : lstSubItemTemp)
            {
                salesOrderSubItemByItem.IdSubItem = subItemTemp.Id;
                salesOrderSubItemByItem = SalesOrderSubItemByItemBussiness.getInstance(mContext).Insert(salesOrderSubItemByItem);
                if(salesOrderSubItemByItem == null) return false;
                lstSalesOrderSubItemByItem.add(salesOrderSubItemByItem);
            }
            for(SubItem subItemTemp : salesOrderItem.SubItemItem)
            {
                for (SalesOrderSubItemByItem salesOrderSubItemByItemTemp : lstSalesOrderSubItemByItem)
                {
                    if(subItemTemp.Id == salesOrderSubItemByItemTemp.IdSubItem)
                    {
                        lstSalesOrderSubItemByItem.remove(salesOrderSubItemByItemTemp);
                        break;
                    }
                }
            }
            for (SalesOrderSubItemByItem salesOrderSubItemByItemTemp : lstSalesOrderSubItemByItem)
            {
                result = SalesOrderSubItemByItemBussiness.getInstance(mContext).Delete(salesOrderSubItemByItemTemp);
                if(!result) break;
            }
        }
        return result;
    }

    public boolean Delete(SalesOrderItem salesOrderItem){
        return SalesOrderItemDataAccess.getInstance(mContext).Delete(salesOrderItem);
    }
}
