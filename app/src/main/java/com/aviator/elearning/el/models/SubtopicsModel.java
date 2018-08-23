package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SubtopicsModel implements Parcelable {
    private int cst_id, cst_topicid;
    private String cst_subtopic, cst_content;

    public SubtopicsModel() {
    }

    protected SubtopicsModel(Parcel in) {
        cst_id = in.readInt();
        cst_topicid = in.readInt();
        cst_subtopic = in.readString();
        cst_content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cst_id);
        dest.writeInt(cst_topicid);
        dest.writeString(cst_subtopic);
        dest.writeString(cst_content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubtopicsModel> CREATOR = new Creator<SubtopicsModel>() {
        @Override
        public SubtopicsModel createFromParcel(Parcel in) {
            return new SubtopicsModel(in);
        }

        @Override
        public SubtopicsModel[] newArray(int size) {
            return new SubtopicsModel[size];
        }
    };

    public int getCst_id() {
        return cst_id;
    }

    public void setCst_id(int cst_id) {
        this.cst_id = cst_id;
    }

    public int getCst_topicid() {
        return cst_topicid;
    }

    public void setCst_topicid(int cst_topicid) {
        this.cst_topicid = cst_topicid;
    }

    public String getCst_subtopic() {
        return cst_subtopic;
    }

    public void setCst_subtopic(String cst_subtopic) {
        this.cst_subtopic = cst_subtopic;
    }

    public String getCst_content() {
        return cst_content;
    }

    public void setCst_content(String cst_content) {
        this.cst_content = cst_content;
    }
}
