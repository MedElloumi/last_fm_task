package com.lastfm.dev.lastfm.models.TopAlbumsResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public class ALlbumArtist {
    @SerializedName("name")
    private String name;
    @SerializedName("mbid")
    private String mbid;
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }
}
