package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SalesOrderItem implements Parcelable {
    public Product Product;
    public ArrayList<Menu> MenuItem;

    public SalesOrderItem(){MenuItem = new ArrayList<>();}

    protected SalesOrderItem(Parcel in) {
        Product = in.readParcelable(com.desenvolvigames.mamaevovo.entities.Product.class.getClassLoader());
        MenuItem = in.createTypedArrayList(Menu.CREATOR);
        if(MenuItem == null)
            MenuItem = new ArrayList<>();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(Product, flags);
        dest.writeTypedList(MenuItem);
    }
}
