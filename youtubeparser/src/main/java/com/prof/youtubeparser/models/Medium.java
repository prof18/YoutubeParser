package com.prof.youtubeparser.models;

/**
 * Created by marco on 5/7/16.
 */
public class Medium {

    private String url;
    private Integer width;
    private Integer height;

    /**
     *
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     *     The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     *
     * @param width
     *     The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     *
     * @return
     *     The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     *     The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Medium{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

}
