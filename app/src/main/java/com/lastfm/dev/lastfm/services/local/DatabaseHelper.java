package com.lastfm.dev.lastfm.services.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lastfm.dev.lastfm.models.LocalAlbum;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 * inspired (I did some copy paste for sure) from: https://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "albums_database";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LocalAlbum.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocalAlbum.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /**
     * Insert an album into our local storage
     * @param localAlbum
     * @return
     */
    public long insertAlbum(LocalAlbum localAlbum) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LocalAlbum.COLUMN_Album_Name, localAlbum.getName());
        values.put(LocalAlbum.COLUMN_Album_Image, localAlbum.getAlbumImage());
        values.put(LocalAlbum.COLUMN_Tracks_Image, localAlbum.getTracksImage());
        values.put(LocalAlbum.COLUMN_Tracks, localAlbum.getTracks());
        values.put(LocalAlbum.COLUMN_Artist, localAlbum.getArtist());

        long id = db.insert(LocalAlbum.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * delete an Album from local storage, while lastFm API
     * dont give any information about the uniqueness of an album name I am considering it unique
     * the other way round: my solution will not work properly and will cause to delete all albums that have the same name from your local storage
     * @param localAlbumName
     */
    public void deleteAlbum(String localAlbumName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LocalAlbum.TABLE_NAME, LocalAlbum.COLUMN_Album_Name + " = ?",
                new String[]{localAlbumName});
        db.close();
    }

    /**
     * check if an album exist in our local database or no
     * We will need this information to check if we should change the star button state {ratingBar color}
     * the star rating bar is responsible to save/delete an album from local storage
     * @param localAlbumName
     */
    public long checkAlbum(String localAlbumName) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, LocalAlbum.TABLE_NAME,LocalAlbum.COLUMN_Album_Name + " = ?",
                new String[]{localAlbumName});
        db.close();
        return count;
    }

    /**
     * @return all albums from local storage so we show them in our main fragment
     */
    public ArrayList<LocalAlbum> getAlbums() {
        ArrayList<LocalAlbum> localAlbums = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + LocalAlbum.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                LocalAlbum localAlbum = new LocalAlbum();
                localAlbum.setName(cursor.getString(cursor.getColumnIndex(LocalAlbum.COLUMN_Album_Name)));
                localAlbum.setArtist(cursor.getString(cursor.getColumnIndex(LocalAlbum.COLUMN_Artist)));
                localAlbum.setAlbumImage(cursor.getString(cursor.getColumnIndex(LocalAlbum.COLUMN_Album_Image)));
                localAlbum.setTracksImage(cursor.getString(cursor.getColumnIndex(LocalAlbum.COLUMN_Tracks_Image)));
                localAlbum.setTracks(cursor.getString(cursor.getColumnIndex(LocalAlbum.COLUMN_Tracks)));

                localAlbums.add(localAlbum);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return localAlbums;
    }
}
