package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.desenvolvigames.mamaevovo.helpers.ProductUnitEnum;

public class Product implements Parcelable
{
    public Long Id;
    public String Description;
    public ProductUnitEnum Unit;
    public Double Price;
    public String Obs;

    public Product(){}

    protected Product(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
        Description = in.readString();
        String unit = in.readString();
        Unit = unit == null ? null : ProductUnitEnum.valueOf(unit);
        if (in.readByte() == 0) {
            Price = null;
        } else {
            Price = in.readDouble();
        }
        Obs = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
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
        dest.writeString(Description);
        dest.writeString(Unit == null ? null : Unit.name());
        if (Price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Price);
        }
        dest.writeString(Obs);
    }

    @Override
    public String toString() {
        return Description;
    }
}
