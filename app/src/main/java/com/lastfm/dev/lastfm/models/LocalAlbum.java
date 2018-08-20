package com.lastfm.dev.lastfm.models;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */

// this is what we are saving in our local storage
public class LocalAlbum {
    private String name;
    private String artist;
    private String albumImage;
    private String tracksImage;
    private String tracks;

    public static final String TABLE_NAME = "albums";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Album_Name = "name";
    public static final String COLUMN_Album_Image = "albumImage";
    public static final String COLUMN_Tracks_Image = "tracksImage";
    public static final String COLUMN_Tracks = "tracks";
    public static final String COLUMN_Artist = "artist";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_Album_Name + " TEXT,"
                    + COLUMN_Album_Image + " TEXT,"
                    + COLUMN_Tracks_Image + " TEXT,"
                    + COLUMN_Artist + " TEXT,"
                    + COLUMN_Tracks + " TEXT"
                    + ")";

    public LocalAlbum(String name, String artist, String albumImage, String tracksImage, String tracks) {
        this.name = name;
        this.artist = artist;
        this.albumImage = albumImage;
        this.tracksImage = tracksImage;
        this.tracks = tracks;
    }

    public LocalAlbum() {
    }

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

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getTracksImage() {
        return tracksImage;
    }

    public void setTracksImage(String tracksImage) {
        this.tracksImage = tracksImage;
    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }
}
