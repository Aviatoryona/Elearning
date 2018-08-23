package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("ALL")
public class CourseModel implements Parcelable {
    private int c_id;
    private String c_uniqid, c_name, c_useremail, c_date, c_approved, c_coverimg;

    public CourseModel() {
    }

    protected CourseModel(Parcel in) {
        c_id = in.readInt();
        c_uniqid = in.readString();
        c_name = in.readString();
        c_useremail = in.readString();
        c_date = in.readString();
        c_approved = in.readString();
        c_coverimg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(c_id);
        dest.writeString(c_uniqid);
        dest.writeString(c_name);
        dest.writeString(c_useremail);
        dest.writeString(c_date);
        dest.writeString(c_approved);
        dest.writeString(c_coverimg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseModel> CREATOR = new Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
        }
    };

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_uniqid() {
        return c_uniqid;
    }

    public void setC_uniqid(String c_uniqid) {
        this.c_uniqid = c_uniqid;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_useremail() {
        return c_useremail;
    }

    public void setC_useremail(String c_useremail) {
        this.c_useremail = c_useremail;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public String getC_approved() {
        return c_approved;
    }

    public void setC_approved(String c_approved) {
        this.c_approved = c_approved;
    }

    public String getC_coverimg() {
        return c_coverimg;
    }

    public void setC_coverimg(String c_coverimg) {
        this.c_coverimg = c_coverimg;
    }
}
