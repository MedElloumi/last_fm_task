package com.lastfm.dev.lastfm.models.TopTracksResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class Atrr implements Parcelable{
    @SerializedName("rank")
    private String rank;

    protected Atrr(Parcel in) {
        rank = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Atrr> CREATOR = new Creator<Atrr>() {
        @Override
        public Atrr createFromParcel(Parcel in) {
            return new Atrr(in);
        }

        @Override
        public Atrr[] newArray(int size) {
            return new Atrr[size];
        }
    };

    public String getRank() {
        return rank;
    }
}
