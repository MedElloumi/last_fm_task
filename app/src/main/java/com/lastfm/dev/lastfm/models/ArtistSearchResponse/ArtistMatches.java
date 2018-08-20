package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArtistMatches {
    @SerializedName("artist")
    private ArrayList<IndividualArtist> artist = new ArrayList<>();

    public ArrayList<IndividualArtist> getArtist() {
        return artist;
    }
}
