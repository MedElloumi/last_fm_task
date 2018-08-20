package com.lastfm.dev.lastfm.models.TopTracksResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class TopTracksResponse {
    @SerializedName("toptracks")
    private TopTracks topTracks;

    public TopTracks getTopTracks() {
        return topTracks;
    }
}
