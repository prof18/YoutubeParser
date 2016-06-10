package com.prof.youtubeparser.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 5/7/16.
 */
public class Main {

    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private List<Item> items = new ArrayList<Item>();

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
     *     The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     *     The etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     *
     * @return
     *     The nextPageToken
     */
    public String getNextPageToken() {
        return nextPageToken;
    }

    /**
     *
     * @param nextPageToken
     *     The nextPageToken
     */
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    /**
     *
     * @return
     *     The regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     *
     * @param regionCode
     *     The regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     *
     * @return
     *     The pageInfo
     */
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    /**
     *
     * @param pageInfo
     *     The pageInfo
     */
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     *
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     *     The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Main{" +
                "kind='" + kind + '\'' +
                ", etag='" + etag + '\'' +
                ", nextPageToken='" + nextPageToken + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", pageInfo=" + pageInfo +
                ", items=" + items +
                '}';
    }

}
