package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SalesOrderItem implements Parcelable {
    public Long Id;
    public Long IdSalesOrder;
    public Product Product;
    public Double Quantidade;
    public ArrayList<SubItem> SubItemItem;

    public SalesOrderItem(){
        SubItemItem = new ArrayList<>();}

    protected SalesOrderItem(Parcel in) {
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
        Product = in.readParcelable(com.desenvolvigames.mamaevovo.entities.Product.class.getClassLoader());
        if (in.readByte() == 0) {
            Quantidade = null;
        } else {
            Quantidade = in.readDouble();
        }
        SubItemItem = in.createTypedArrayList(SubItem.CREATOR);
        if(SubItemItem == null)
            SubItemItem = new ArrayList<>();
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
        dest.writeParcelable(Product, flags);
        if (Quantidade == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Quantidade);
        }
        dest.writeTypedList(SubItemItem);
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
        StringBuilder sbResult = new StringBuilder();
        sbResult.append(Product.Description);
        sbResult.append(" (");
        sbResult.append(Quantidade);
        sbResult.append(" * ");
        sbResult.append(Product.Price);
        sbResult.append(" = ");
        sbResult.append(" R$ ");
        sbResult.append(Product.Price * Quantidade);
        sbResult.append(")");
        return sbResult.toString();
    }
}
