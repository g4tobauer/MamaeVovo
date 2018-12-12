package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;

import com.desenvolvigames.mamaevovo.dataAccess.MenuDataAccess;
import com.desenvolvigames.mamaevovo.entities.Menu;

import java.util.ArrayList;

public class MenuBussiness {

    private static MenuBussiness mInstance;
    private Context mContext;

    private MenuBussiness(){}

    public static MenuBussiness getInstance(Context context)
    {
        if(mInstance==null)
            mInstance = new MenuBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<Menu> Get(Menu menu)
    {
        return MenuDataAccess.getInstance(mContext).Get(menu);
    }

    public Menu Insert(Menu menu)
    {
        return MenuDataAccess.getInstance(mContext).Insert(menu);
    }

    public boolean Update(Menu menu)
    {
        return MenuDataAccess.getInstance(mContext).Update(menu);
    }

    public boolean Delete(Menu menu)
    {
        return MenuDataAccess.getInstance(mContext).Delete(menu);
    }
}
