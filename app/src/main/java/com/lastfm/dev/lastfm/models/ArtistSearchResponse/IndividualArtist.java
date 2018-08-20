package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IndividualArtist {
    @SerializedName("name")
    private String name;
    @SerializedName("listeners")
    private String listeners;
    @SerializedName("mbid")
    private String mbid;
    @SerializedName("url")
    private String url;
    @SerializedName("streamable")
    private String streamable;
    @SerializedName("image")
    private ArrayList<Image> image = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getListeners() {
        return listeners;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public String getStreamable() {
        return streamable;
    }

    public ArrayList<Image> getImage() {
        return image;
    }
}
