package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import com.google.gson.annotations.SerializedName;

public class Query {
    @SerializedName("#text")
    private String text;
    @SerializedName("role")
    private String role;
    @SerializedName("searchTerms")
    private String searchTerms;
    @SerializedName("startPage")
    private String startPage;

    public String getText() {
        return text;
    }

    public String getRole() {
        return role;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public String getStartPage() {
        return startPage;
    }
}
