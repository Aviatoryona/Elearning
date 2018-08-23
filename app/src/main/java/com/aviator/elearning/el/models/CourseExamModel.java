package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("ALL")
public class CourseExamModel implements Parcelable {
    private CourseModel courseModel;
    private ExamModel examModel;

    public CourseExamModel() {
    }

    protected CourseExamModel(Parcel in) {
        courseModel = in.readParcelable(CourseModel.class.getClassLoader());
        examModel = in.readParcelable(ExamModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(courseModel, flags);
        dest.writeParcelable(examModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseExamModel> CREATOR = new Creator<CourseExamModel>() {
        @Override
        public CourseExamModel createFromParcel(Parcel in) {
            return new CourseExamModel(in);
        }

        @Override
        public CourseExamModel[] newArray(int size) {
            return new CourseExamModel[size];
        }
    };

    public CourseModel getCourseModel() {
        return courseModel;
    }

    public void setCourseModel(CourseModel courseModel) {
        this.courseModel = courseModel;
    }

    public ExamModel getExamModel() {
        return examModel;
    }

    public void setExamModel(ExamModel examModel) {
        this.examModel = examModel;
    }
}
