package com.lastfm.dev.lastfm.models.TopAlbumsResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public class TopAlbumsResponse {
    @SerializedName("topalbums")
    private TopAlbums topAlbums;

    public TopAlbums getTopAlbums() {
        return topAlbums;
    }
}
