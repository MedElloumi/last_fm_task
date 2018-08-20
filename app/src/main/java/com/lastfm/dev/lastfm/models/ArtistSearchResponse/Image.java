package com.lastfm.dev.lastfm.models.ArtistSearchResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable{
    @SerializedName("#text")
    private String text;
    @SerializedName("size")
    private String size;

    protected Image(Parcel in) {
        text = in.readString();
        size = in.readString();
    }

    public Image(String text) {
        this.text = text;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getText() {
        return text;
    }

    public String getSize() {
        return size;
    }

    public void setText(String text) {
        this.text = text;
    }
}
