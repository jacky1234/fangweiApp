package com.jacky.lebeauty.ui.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class Image implements Parcelable ,Serializable{
    private String origin;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.origin);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.origin = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
