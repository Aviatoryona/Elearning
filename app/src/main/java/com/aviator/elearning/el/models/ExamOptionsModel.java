package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ExamOptionsModel implements Parcelable{
    private int opt_id,opt_qnid;
    private String opt_option1,opt_option2,opt_option3,opt_option4,opt_option5,opt_option6,opt_optionAnswer,opt_examuniqid;

    public ExamOptionsModel() {
    }

    protected ExamOptionsModel(Parcel in) {
        opt_id = in.readInt();
        opt_qnid = in.readInt();
        opt_option1 = in.readString();
        opt_option2 = in.readString();
        opt_option3 = in.readString();
        opt_option4 = in.readString();
        opt_option5 = in.readString();
        opt_option6 = in.readString();
        opt_optionAnswer = in.readString();
        opt_examuniqid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(opt_id);
        dest.writeInt(opt_qnid);
        dest.writeString(opt_option1);
        dest.writeString(opt_option2);
        dest.writeString(opt_option3);
        dest.writeString(opt_option4);
        dest.writeString(opt_option5);
        dest.writeString(opt_option6);
        dest.writeString(opt_optionAnswer);
        dest.writeString(opt_examuniqid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExamOptionsModel> CREATOR = new Creator<ExamOptionsModel>() {
        @Override
        public ExamOptionsModel createFromParcel(Parcel in) {
            return new ExamOptionsModel(in);
        }

        @Override
        public ExamOptionsModel[] newArray(int size) {
            return new ExamOptionsModel[size];
        }
    };

    public int getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(int opt_id) {
        this.opt_id = opt_id;
    }

    public int getOpt_qnid() {
        return opt_qnid;
    }

    public void setOpt_qnid(int opt_qnid) {
        this.opt_qnid = opt_qnid;
    }

    public String getOpt_option1() {
        return opt_option1;
    }

    public void setOpt_option1(String opt_option1) {
        this.opt_option1 = opt_option1;
    }

    public String getOpt_option2() {
        return opt_option2;
    }

    public void setOpt_option2(String opt_option2) {
        this.opt_option2 = opt_option2;
    }

    public String getOpt_option3() {
        return opt_option3;
    }

    public void setOpt_option3(String opt_option3) {
        this.opt_option3 = opt_option3;
    }

    public String getOpt_option4() {
        return opt_option4;
    }

    public void setOpt_option4(String opt_option4) {
        this.opt_option4 = opt_option4;
    }

    public String getOpt_option5() {
        return opt_option5;
    }

    public void setOpt_option5(String opt_option5) {
        this.opt_option5 = opt_option5;
    }

    public String getOpt_option6() {
        return opt_option6;
    }

    public void setOpt_option6(String opt_option6) {
        this.opt_option6 = opt_option6;
    }

    public String getOpt_optionAnswer() {
        return opt_optionAnswer;
    }

    public void setOpt_optionAnswer(String opt_optionAnswer) {
        this.opt_optionAnswer = opt_optionAnswer;
    }

    public String getOpt_examuniqid() {
        return opt_examuniqid;
    }

    public void setOpt_examuniqid(String opt_examuniqid) {
        this.opt_examuniqid = opt_examuniqid;
    }
}
