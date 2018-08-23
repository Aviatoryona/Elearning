package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("ALL")
public class ExamModel implements Parcelable {
    private int e_id;
    double e_duration;
    private String e_courseuniqid, e_useremail, e_status, e_coverimg, e_examuniqid, e_approved, e_date;

    public ExamModel() {
    }

    protected ExamModel(Parcel in) {
        e_id = in.readInt();
        e_courseuniqid = in.readString();
        e_useremail = in.readString();
        e_status = in.readString();
        e_coverimg = in.readString();
        e_examuniqid = in.readString();
        e_approved = in.readString();
        e_date = in.readString();
        e_duration=in.readDouble();
    }

    public static final Creator<ExamModel> CREATOR = new Creator<ExamModel>() {
        @Override
        public ExamModel createFromParcel(Parcel in) {
            return new ExamModel(in);
        }

        @Override
        public ExamModel[] newArray(int size) {
            return new ExamModel[size];
        }
    };

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getE_courseuniqid() {
        return e_courseuniqid;
    }

    public void setE_courseuniqid(String e_courseuniqid) {
        this.e_courseuniqid = e_courseuniqid;
    }

    public String getE_useremail() {
        return e_useremail;
    }

    public void setE_useremail(String e_useremail) {
        this.e_useremail = e_useremail;
    }

    public String getE_status() {
        return e_status;
    }

    public void setE_status(String e_status) {
        this.e_status = e_status;
    }

    public String getE_coverimg() {
        return e_coverimg;
    }

    public void setE_coverimg(String e_coverimg) {
        this.e_coverimg = e_coverimg;
    }

    public String getE_examuniqid() {
        return e_examuniqid;
    }

    public void setE_examuniqid(String e_examuniqid) {
        this.e_examuniqid = e_examuniqid;
    }

    public String getE_approved() {
        return e_approved;
    }

    public void setE_approved(String e_approved) {
        this.e_approved = e_approved;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    public double getE_duration() {
        return e_duration;
    }

    public void setE_duration(double e_duration) {
        this.e_duration = e_duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(e_id);
        dest.writeDouble(e_duration);
        dest.writeString(e_courseuniqid);
        dest.writeString(e_useremail);
        dest.writeString(e_status);
        dest.writeString(e_coverimg);
        dest.writeString(e_examuniqid);
        dest.writeString(e_approved);
        dest.writeString(e_date);
    }
}
