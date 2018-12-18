package com.desenvolvigames.mamaevovo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class SubItem implements Parcelable {
    public Long Id;
    public String Description;
    public Boolean Active;

    public SubItem(){}

    protected SubItem(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readLong();
        }
        Description = in.readString();
        Active = in.readByte() != 0;
    }

    public static final Creator<SubItem> CREATOR = new Creator<SubItem>() {
        @Override
        public SubItem createFromParcel(Parcel in) {
            return new SubItem(in);
        }

        @Override
        public SubItem[] newArray(int size) {
            return new SubItem[size];
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

    @Override
    public String toString() {
        return Description;
    }
}
