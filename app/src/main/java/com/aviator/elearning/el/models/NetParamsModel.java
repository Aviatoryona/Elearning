package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NetParamsModel implements Parcelable{
    private String key;
    private String value;

    public NetParamsModel() {
    }

    protected NetParamsModel(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetParamsModel> CREATOR = new Creator<NetParamsModel>() {
        @Override
        public NetParamsModel createFromParcel(Parcel in) {
            return new NetParamsModel(in);
        }

        @Override
        public NetParamsModel[] newArray(int size) {
            return new NetParamsModel[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
