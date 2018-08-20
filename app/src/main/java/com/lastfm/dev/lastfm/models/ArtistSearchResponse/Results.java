package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("opensearch:Query")
    private Query query;
    @SerializedName("opensearch:totalResults")
    private String totalResults;
    @SerializedName("opensearch:startIndex")
    private String startIndex;
    @SerializedName("opensearch:itemsPerPage")
    private String itemsPerPage;
    @SerializedName("artistmatches")
    private ArtistMatches artistMatches;
    @SerializedName("@attr")
    private Attr atrr;

    public Query getQuery() {
        return query;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public String getItemsPerPage() {
        return itemsPerPage;
    }

    public ArtistMatches getArtistMatches() {
        return artistMatches;
    }

    public Attr getAtrr() {
        return atrr;
    }
}
