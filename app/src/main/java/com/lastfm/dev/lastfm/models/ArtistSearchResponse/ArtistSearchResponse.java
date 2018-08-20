package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import com.google.gson.annotations.SerializedName;

public class ArtistSearchResponse {
    @SerializedName("results")
    private Results results;

    public Results getResults() {
        return results;
    }
}
