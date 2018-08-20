package com.lastfm.dev.lastfm.services.local;

import android.content.Context;

import com.lastfm.dev.lastfm.models.LocalAlbum;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class LocalAlbumService {

    public void insertLocalAlbum(Context context, LocalAlbum localAlbum){
        DatabaseHelper db = new DatabaseHelper(context);
        db.insertAlbum(localAlbum);
    }

    public void deleteLocalAlbum(Context context, String localAlbumName){
        DatabaseHelper db = new DatabaseHelper(context);
        db.deleteAlbum(localAlbumName);
    }

    public long checkLocalAlbumExistence(Context context, String localAlbumName){
        DatabaseHelper db = new DatabaseHelper(context);
        return db.checkAlbum(localAlbumName);
    }

    public ArrayList<LocalAlbum> getAllLocalAlbums(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getAlbums();
    }

}
