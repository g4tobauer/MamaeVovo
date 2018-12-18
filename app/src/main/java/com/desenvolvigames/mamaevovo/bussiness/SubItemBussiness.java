package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.SubItemDataAccess;
import com.desenvolvigames.mamaevovo.entities.SubItem;

import java.util.ArrayList;

public class SubItemBussiness {

    private static SubItemBussiness mInstance;
    private Context mContext;

    private SubItemBussiness(){}

    public static SubItemBussiness getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = new SubItemBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<SubItem> Get(SubItem subItem)
    {
        return SubItemDataAccess.getInstance(mContext).Get(subItem);
    }

    public SubItem Insert(SubItem subItem)
    {
        return SubItemDataAccess.getInstance(mContext).Insert(subItem);
    }

    public boolean Update(SubItem subItem)
    {
        return SubItemDataAccess.getInstance(mContext).Update(subItem);
    }

    public boolean Delete(SubItem subItem)
    {
        return SubItemDataAccess.getInstance(mContext).Delete(subItem);
    }
}
