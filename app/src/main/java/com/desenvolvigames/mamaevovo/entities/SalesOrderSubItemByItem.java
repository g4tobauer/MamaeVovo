package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class SalesOrderSubItemByItem implements Parcelable {
    public Long Id;
    public Long IdSalesOrder;
    public Long IdSalesOrderItem;
    public Long IdSubItem;

    public SalesOrderSubItemByItem(){}

    protected SalesOrderSubItemByItem(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
        if (in.readByte() == 0) {
            IdSalesOrder = null;
        } else {
            IdSalesOrder = in.readLong();
        }
        if (in.readByte() == 0) {
            IdSalesOrderItem = null;
        } else {
            IdSalesOrderItem = in.readLong();
        }
        if (in.readByte() == 0) {
            IdSubItem = null;
        } else {
            IdSubItem = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(Id);
        }
        if (IdSalesOrder == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(IdSalesOrder);
        }
        if (IdSalesOrderItem == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(IdSalesOrderItem);
        }
        if (IdSubItem == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(IdSubItem);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalesOrderSubItemByItem> CREATOR = new Creator<SalesOrderSubItemByItem>() {
        @Override
        public SalesOrderSubItemByItem createFromParcel(Parcel in) {
            return new SalesOrderSubItemByItem(in);
        }

        @Override
        public SalesOrderSubItemByItem[] newArray(int size) {
            return new SalesOrderSubItemByItem[size];
        }
    };
}
