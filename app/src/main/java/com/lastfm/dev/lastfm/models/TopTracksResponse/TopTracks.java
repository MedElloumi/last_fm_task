package com.lastfm.dev.lastfm.models.TopTracksResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.lastfm.dev.lastfm.models.TopAlbumsResponse.AlbumAtrr;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class TopTracks implements Parcelable{
    @SerializedName("track")
    private ArrayList<IndividualTrack> individualTrack;
    @SerializedName("@attr")
    private AlbumAtrr attr;

    public TopTracks() {
    }

    public TopTracks(ArrayList<IndividualTrack> individualTrack) {
        this.individualTrack = individualTrack;
    }

    protected TopTracks(Parcel in) {
        individualTrack = in.createTypedArrayList(IndividualTrack.CREATOR);
    }

    public static final Creator<TopTracks> CREATOR = new Creator<TopTracks>() {
        @Override
        public TopTracks createFromParcel(Parcel in) {
            return new TopTracks(in);
        }

        @Override
        public TopTracks[] newArray(int size) {
            return new TopTracks[size];
        }
    };

    public ArrayList<IndividualTrack> getIndividualTrack() {
        return individualTrack;
    }

    public AlbumAtrr getAttr() {
        return attr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(individualTrack);
    }

    public void setIndividualTrack(ArrayList<IndividualTrack> individualTrack) {
        this.individualTrack = individualTrack;
    }

    public void setAttr(AlbumAtrr attr) {
        this.attr = attr;
    }
}
