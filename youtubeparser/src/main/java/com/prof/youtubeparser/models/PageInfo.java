package com.prof.youtubeparser.models;

/**
 * Created by marco on 5/7/16.
 */
public class PageInfo {

    private Integer totalResults;
    private Integer resultsPerPage;

    /**
     *
     * @return
     *     The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     *     The totalResults
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     *     The resultsPerPage
     */
    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     *
     * @param resultsPerPage
     *     The resultsPerPage
     */
    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "totalResults=" + totalResults +
                ", resultsPerPage=" + resultsPerPage +
                '}';
    }

}
