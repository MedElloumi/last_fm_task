package com.lastfm.dev.lastfm.models.TopTracksResponse;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class Track {
    private ArrayList<IndividualTrack> individualTrack = new ArrayList<>();

    public ArrayList<IndividualTrack> getIndividualTrack() {
        return individualTrack;
    }
}
