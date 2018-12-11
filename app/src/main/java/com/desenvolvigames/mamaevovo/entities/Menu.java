package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {
    public Long Id;
    public String Description;
    public boolean Active;

    public Menu(){}

    protected Menu(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
        Description = in.readString();
        Active = in.readByte() != 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
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
        dest.writeByte((byte) (Active ? 1 : 0));
    }
}
