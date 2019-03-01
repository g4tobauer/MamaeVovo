package com.desenvolvigames.mamaevovo.bussiness;

import android.content.Context;
import com.desenvolvigames.mamaevovo.dataAccess.MovementDateDataAccess;
import com.desenvolvigames.mamaevovo.entities.MovementDate;
import com.desenvolvigames.mamaevovo.helpers.DateHelper;
import com.desenvolvigames.mamaevovo.helpers.Filters.MovementDateFilter;

import java.util.ArrayList;

public class MovementDateBussiness {

    private static MovementDateBussiness mInstance;
    private Context mContext;

    private MovementDateBussiness(){}

    public static MovementDateBussiness getInstance(Context context){
        if(mInstance==null)
            mInstance = new MovementDateBussiness();
        mInstance.SaveContext(context);
        return mInstance;
    }

    private void SaveContext(Context context)
    {
        mContext = context;
    }

    public ArrayList<MovementDate> Get(MovementDateFilter movementDateFilter){
        movementDateFilter.InitialDate = DateHelper.addDaysToDate(movementDateFilter.FinalDate, movementDateFilter.BaseDateDays);
        return MovementDateDataAccess.getInstance(mContext).Get(movementDateFilter);
    }

    public MovementDate Insert(MovementDate movementDate){
        return MovementDateDataAccess.getInstance(mContext).Insert(movementDate);
    }

    public boolean Delete(MovementDate movementDate){
        return MovementDateDataAccess.getInstance(mContext).Delete(movementDate);
    }
}
