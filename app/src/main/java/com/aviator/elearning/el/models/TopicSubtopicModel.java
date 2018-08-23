package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TopicSubtopicModel implements Parcelable {
    private TopicsModel topicsModel;
    private ArrayList<SubtopicsModel> subtopicsModelArrayList;

    public TopicSubtopicModel() {
    }

    protected TopicSubtopicModel(Parcel in) {
        topicsModel = in.readParcelable(TopicsModel.class.getClassLoader());
        subtopicsModelArrayList = in.createTypedArrayList(SubtopicsModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(topicsModel, flags);
        dest.writeTypedList(subtopicsModelArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopicSubtopicModel> CREATOR = new Creator<TopicSubtopicModel>() {
        @Override
        public TopicSubtopicModel createFromParcel(Parcel in) {
            return new TopicSubtopicModel(in);
        }

        @Override
        public TopicSubtopicModel[] newArray(int size) {
            return new TopicSubtopicModel[size];
        }
    };

    public TopicsModel getTopicsModel() {
        return topicsModel;
    }

    public void setTopicsModel(TopicsModel topicsModel) {
        this.topicsModel = topicsModel;
    }

    public ArrayList<SubtopicsModel> getSubtopicsModelArrayList() {
        return subtopicsModelArrayList;
    }

    public void setSubtopicsModelArrayList(ArrayList<SubtopicsModel> subtopicsModelArrayList) {
        this.subtopicsModelArrayList = subtopicsModelArrayList;
    }
}
