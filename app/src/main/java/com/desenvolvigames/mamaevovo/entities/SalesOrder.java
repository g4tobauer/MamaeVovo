package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SalesOrder implements Parcelable {
    public Long Id;
    public Long IdDate;
    public ArrayList<SalesOrderItem> SalesOrderItem;

    public SalesOrder(){}

    protected SalesOrder(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
        if (in.readByte() == 0) {
            IdDate = null;
        } else {
            IdDate = in.readLong();
        }
        SalesOrderItem = in.createTypedArrayList(com.desenvolvigames.mamaevovo.entities.SalesOrderItem.CREATOR);
    }

    public static final Creator<SalesOrder> CREATOR = new Creator<SalesOrder>() {
        @Override
        public SalesOrder createFromParcel(Parcel in) {
            return new SalesOrder(in);
        }

        @Override
        public SalesOrder[] newArray(int size) {
            return new SalesOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(Id);
        }
        if (IdDate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(IdDate);
        }
        dest.writeTypedList(SalesOrderItem);
    }
}
