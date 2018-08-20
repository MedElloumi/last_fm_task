package com.lastfm.dev.lastfm.models.TopAlbumsResponse;

import com.google.gson.annotations.SerializedName;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.Image;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public class Album {
    @SerializedName("name")
    private String name;
    @SerializedName("playCount")
    private String playCount;
    @SerializedName("url")
    private String url;
    @SerializedName("artist")
    private ALlbumArtist albumArtist;
    @SerializedName("image")
    private ArrayList<Image> image = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getPlayCount() {
        return playCount;
    }

    public String getUrl() {
        return url;
    }

    public ALlbumArtist getAlbumArtist() {
        return albumArtist;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbumArtist(ALlbumArtist albumArtist) {
        this.albumArtist = albumArtist;
    }
}
