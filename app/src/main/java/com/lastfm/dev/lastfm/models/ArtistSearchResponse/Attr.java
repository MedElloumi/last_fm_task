package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import com.google.gson.annotations.SerializedName;

public class Attr {
    @SerializedName("for")
    private String For;

    public String getFor() {
        return For;
    }
}
