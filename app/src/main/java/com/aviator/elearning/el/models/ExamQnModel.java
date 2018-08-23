package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ExamQnModel implements Parcelable{
    private int qn_id;
    private String qn_examuniqid,qn_examqn;

    public ExamQnModel() {
    }

    protected ExamQnModel(Parcel in) {
        qn_id = in.readInt();
        qn_examuniqid = in.readString();
        qn_examqn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(qn_id);
        dest.writeString(qn_examuniqid);
        dest.writeString(qn_examqn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExamQnModel> CREATOR = new Creator<ExamQnModel>() {
        @Override
        public ExamQnModel createFromParcel(Parcel in) {
            return new ExamQnModel(in);
        }

        @Override
        public ExamQnModel[] newArray(int size) {
            return new ExamQnModel[size];
        }
    };

    public int getQn_id() {
        return qn_id;
    }

    public void setQn_id(int qn_id) {
        this.qn_id = qn_id;
    }

    public String getQn_examuniqid() {
        return qn_examuniqid;
    }

    public void setQn_examuniqid(String qn_examuniqid) {
        this.qn_examuniqid = qn_examuniqid;
    }

    public String getQn_examqn() {
        return qn_examqn;
    }

    public void setQn_examqn(String qn_examqn) {
        this.qn_examqn = qn_examqn;
    }
}
