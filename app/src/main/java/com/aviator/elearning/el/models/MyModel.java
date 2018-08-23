package com.aviator.elearning.el.models;

/**
 * Created by krupenghetiya on 03/02/17.Tra
 */

public class MyModel {
    public int position;
    private  String image_url;
    private String video_url;
    private  String name;
    private String numLikes,description;

    public MyModel(String video_url, String image_url, String name, String numLikes, String description) {
        this.image_url = image_url;
        this.video_url = video_url;
        this.name = name;
        this.numLikes = numLikes;
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(String numLikes) {
        this.numLikes = numLikes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
