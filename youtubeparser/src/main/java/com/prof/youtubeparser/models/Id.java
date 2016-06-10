package com.prof.youtubeparser.models;

/**
 * Created by marco on 5/7/16.
 */
public class Id {

    private String kind;
    private String videoId;

    /**
     *
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     *     The videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     *
     * @param videoId
     *     The videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "Id{" +
                "kind='" + kind + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }

}
