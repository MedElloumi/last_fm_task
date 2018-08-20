package com.lastfm.dev.lastfm.models.TopAlbumsResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public class TopAlbums {
    @SerializedName("album")
    private ArrayList<Album> album = new ArrayList<>();
    @SerializedName("@attr")
    private AlbumAtrr attr;

    public ArrayList<Album> getAlbum() {
        return album;
    }

    public AlbumAtrr getAttr() {
        return attr;
    }
}
