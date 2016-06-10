package com.prof.youtubeparser.models;

/**
 * Created by marco on 5/7/16.
 */
public class Video {

    private String title;
    private String videoId;
    private String coverLink;
    private String date;

    public Video(String title, String videoId, String coverLink, String date) {

        this.title = title;
        this.videoId = videoId;
        this.coverLink = coverLink;
        this.date = date;

    }

    public String getTitle() {

        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public String getDate() {

        return date;

    }

    public void setDate(String date) {

        this.date = date;

    }

    public String getVideoId() {

        return videoId;

    }

    public void setVideoId(String videoId) {

        this.videoId = videoId;

    }

    public String getCoverLink() {

        return coverLink;

    }

    public void setCoverLink(String coverLink) {

        this.coverLink = coverLink;

    }

    @Override
    public String toString() {
        return "Video { " +
                "title= '" + title + '\'' +
                ", videoId= '" + videoId + '\'' +
                ", coverLink= '" + coverLink + '\'' +
                ", date= '" + date + '\'' +
                '}';
    }

}
