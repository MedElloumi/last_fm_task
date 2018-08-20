package com.lastfm.dev.lastfm.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
// Requested Album Object that we will pass to the detail album fragment
public class RequestedAlbum implements Parcelable {
    public String name;
    public String artist;
    public String image;
    public String mbid;

    public RequestedAlbum(String name, String artist, String image, String mbid) {
        this.name = name;
        this.artist = artist;
        this.image = image;
        this.mbid = mbid;
    }

    public RequestedAlbum(String name, String artist, String image) {
        this.name = name;
        this.artist = artist;
        this.image = image;
    }

    protected RequestedAlbum(Parcel in) {
        name = in.readString();
        artist = in.readString();
        image = in.readString();
    }

    public static final Creator<RequestedAlbum> CREATOR = new Creator<RequestedAlbum>() {
        @Override
        public RequestedAlbum createFromParcel(Parcel in) {
            return new RequestedAlbum(in);
        }

        @Override
        public RequestedAlbum[] newArray(int size) {
            return new RequestedAlbum[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(artist);
        parcel.writeString(image);
    }
}
