package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MovementDate implements Parcelable {

    public Long Id;
    public Date Date;

    public MovementDate(){}

    protected MovementDate(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
    }

    public static final Creator<MovementDate> CREATOR = new Creator<MovementDate>() {
        @Override
        public MovementDate createFromParcel(Parcel in) {
            return new MovementDate(in);
        }

        @Override
        public MovementDate[] newArray(int size) {
            return new MovementDate[size];
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
    }
}
