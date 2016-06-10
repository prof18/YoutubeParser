package com.prof.youtubeparser.models;

/**
 * Created by marco on 5/7/16.
 */
public class Item {

    private String kind;
    private String etag;
    private Id id;
    private Snippet snippet;

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
     *     The id
     */
    public Id getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Id id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The snippet
     */
    public Snippet getSnippet() {
        return snippet;
    }

    /**
     *
     * @param snippet
     *     The snippet
     */
    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    @Override
    public String toString() {
        return "Item{" +
                "kind='" + kind + '\'' +
                ", etag='" + etag + '\'' +
                ", id=" + id +
                ", snippet=" + snippet +
                '}';
    }

}
