package com.desenvolvigames.mamaevovo.helpers.Filters;


import java.util.Date;

public class MovementDateFilter {

    public static int INTERVALDATEBYID = 1;
    public static int INTERVALDATEBYDAYS = 2;

    public Long Id;
    public Integer IntervalDateType;
    public Integer BaseDateDays;
    public Date InitialDate;
    public Date FinalDate;

    public MovementDateFilter(){}

    public MovementDateFilter(Integer IntervalDateType, Date FinalDate, Integer BaseDateDays)
    {
        this.IntervalDateType = IntervalDateType;
        this.FinalDate = FinalDate;
        this.BaseDateDays = BaseDateDays;
    }
}
