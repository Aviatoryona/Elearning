package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ExamQAModel implements Parcelable {
    private ExamQnModel examQnModel;
    private ExamOptionsModel examOptionsModel;

    public ExamQAModel() {
    }

    protected ExamQAModel(Parcel in) {
        examQnModel = in.readParcelable(ExamQnModel.class.getClassLoader());
        examOptionsModel = in.readParcelable(ExamOptionsModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(examQnModel, flags);
        dest.writeParcelable(examOptionsModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExamQAModel> CREATOR = new Creator<ExamQAModel>() {
        @Override
        public ExamQAModel createFromParcel(Parcel in) {
            return new ExamQAModel(in);
        }

        @Override
        public ExamQAModel[] newArray(int size) {
            return new ExamQAModel[size];
        }
    };

    public ExamQnModel getExamQnModel() {
        return examQnModel;
    }

    public void setExamQnModel(ExamQnModel examQnModel) {
        this.examQnModel = examQnModel;
    }

    public ExamOptionsModel getExamOptionsModel() {
        return examOptionsModel;
    }

    public void setExamOptionsModel(ExamOptionsModel examOptionsModel) {
        this.examOptionsModel = examOptionsModel;
    }
}
