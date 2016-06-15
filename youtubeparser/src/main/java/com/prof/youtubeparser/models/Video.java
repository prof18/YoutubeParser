/*
 *    The MIT License (MIT)
 *
 *    Copyright (c) 2016 Marco Gomiero
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOFTWARE.
 */

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
