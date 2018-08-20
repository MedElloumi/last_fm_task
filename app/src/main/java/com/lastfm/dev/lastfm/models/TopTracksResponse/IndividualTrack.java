package com.lastfm.dev.lastfm.models.TopTracksResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.Image;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class IndividualTrack implements Parcelable{
    @SerializedName("name")
    private String name;
    @SerializedName("playcount")
    private String playCount;
    @SerializedName("listeners")
    private String listeners;
    @SerializedName("mbid")
    private String mbid;
    @SerializedName("url")
    private String url;
    @SerializedName("streamable")
    private String streamable;
    @SerializedName("artist")
    private Artist artist;
    @SerializedName("image")
    private ArrayList<Image> image = new ArrayList<>();
    @SerializedName("@attr")
    private Atrr attr;

    public IndividualTrack() {
    }

    public IndividualTrack(String name, ArrayList<Image> image) {
        this.name = name;
        this.image = image;
    }

    protected IndividualTrack(Parcel in) {
        name = in.readString();
        playCount = in.readString();
        listeners = in.readString();
        mbid = in.readString();
        url = in.readString();
        streamable = in.readString();
        artist = in.readParcelable(Artist.class.getClassLoader());
        image = in.createTypedArrayList(Image.CREATOR);
        attr = in.readParcelable(Atrr.class.getClassLoader());
    }

    public static final Creator<IndividualTrack> CREATOR = new Creator<IndividualTrack>() {
        @Override
        public IndividualTrack createFromParcel(Parcel in) {
            return new IndividualTrack(in);
        }

        @Override
        public IndividualTrack[] newArray(int size) {
            return new IndividualTrack[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPlayCount() {
        return playCount;
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

    public Artist getArtist() {
        return artist;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public Atrr getAttr() {
        return attr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(playCount);
        parcel.writeString(listeners);
        parcel.writeString(mbid);
        parcel.writeString(url);
        parcel.writeString(streamable);
        parcel.writeParcelable(artist, i);
        parcel.writeTypedList(image);
        parcel.writeParcelable(attr, i);
    }
}
