package com.lastfm.dev.lastfm.models.TopAlbumsResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public class AlbumAtrr implements Parcelable{
    @SerializedName("artist")
    private String artist;
    @SerializedName("page")
    private String page;
    @SerializedName("perPage")
    private String perPage;
    @SerializedName("totalPages")
    private String totalPages;
    @SerializedName("total")
    private String total;

    protected AlbumAtrr(Parcel in) {
        artist = in.readString();
        page = in.readString();
        perPage = in.readString();
        totalPages = in.readString();
        total = in.readString();
    }

    public static final Creator<AlbumAtrr> CREATOR = new Creator<AlbumAtrr>() {
        @Override
        public AlbumAtrr createFromParcel(Parcel in) {
            return new AlbumAtrr(in);
        }

        @Override
        public AlbumAtrr[] newArray(int size) {
            return new AlbumAtrr[size];
        }
    };

    public String getArtist() {
        return artist;
    }

    public String getPage() {
        return page;
    }

    public String getPerPage() {
        return perPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public String getTotal() {
        return total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artist);
        parcel.writeString(page);
        parcel.writeString(perPage);
        parcel.writeString(totalPages);
        parcel.writeString(total);
    }
}
