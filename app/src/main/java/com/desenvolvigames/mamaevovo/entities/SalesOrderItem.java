package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SalesOrderItem implements Parcelable {
    public Long Id;
    public Product Product;
    public ArrayList<Menu> MenuItem;

    public SalesOrderItem(){MenuItem = new ArrayList<>();}

    protected SalesOrderItem(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
        Product = in.readParcelable(com.desenvolvigames.mamaevovo.entities.Product.class.getClassLoader());
        MenuItem = in.createTypedArrayList(Menu.CREATOR);
        if(MenuItem == null)
            MenuItem = new ArrayList<>();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(Id);
        }
        dest.writeParcelable(Product, flags);
        dest.writeTypedList(MenuItem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalesOrderItem> CREATOR = new Creator<SalesOrderItem>() {
        @Override
        public SalesOrderItem createFromParcel(Parcel in) {
            return new SalesOrderItem(in);
        }

        @Override
        public SalesOrderItem[] newArray(int size) {
            return new SalesOrderItem[size];
        }
    };

    @Override
    public String toString() {
        return Product.Description;
    }
}
