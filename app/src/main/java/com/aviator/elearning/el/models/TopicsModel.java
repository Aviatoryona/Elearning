package com.aviator.elearning.el.models;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("ALL")
public class TopicsModel implements Parcelable{
    private int topicid;
    private String courseUniqId, topic;

    public TopicsModel() {
    }

    protected TopicsModel(Parcel in) {
        topicid = in.readInt();
        courseUniqId = in.readString();
        topic = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(topicid);
        dest.writeString(courseUniqId);
        dest.writeString(topic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopicsModel> CREATOR = new Creator<TopicsModel>() {
        @Override
        public TopicsModel createFromParcel(Parcel in) {
            return new TopicsModel(in);
        }

        @Override
        public TopicsModel[] newArray(int size) {
            return new TopicsModel[size];
        }
    };

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getCourseUniqId() {
        return courseUniqId;
    }

    public void setCourseUniqId(String courseUniqId) {
        this.courseUniqId = courseUniqId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
