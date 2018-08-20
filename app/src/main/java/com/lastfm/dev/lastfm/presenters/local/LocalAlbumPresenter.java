package com.lastfm.dev.lastfm.presenters.local;

import android.content.Context;
import android.os.AsyncTask;

import com.lastfm.dev.lastfm.models.LocalAlbum;
import com.lastfm.dev.lastfm.services.local.LocalAlbumService;

import java.util.ArrayList;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class LocalAlbumPresenter extends AsyncTask<Void, String, LocalAlbumPresenter.CustomObject> {
    private Context context;
    private LocalAlbum localAlbum;
    private LocalAlbumInterface localAlbumInterfaceCallback;
    private String method;

    public LocalAlbumPresenter(LocalAlbumInterface localAlbumInterfaceCallback, Context context, LocalAlbum localAlbum, String method) {
        this.localAlbumInterfaceCallback = localAlbumInterfaceCallback;
        this.context = context;
        this.localAlbum = localAlbum;
        this.method = method;
    }

    public LocalAlbumPresenter(Context context, LocalAlbumInterface localAlbumInterfaceCallback, String method) {
        this.context = context;
        this.localAlbumInterfaceCallback = localAlbumInterfaceCallback;
        this.method = method;
    }

    @Override
    protected void onPostExecute(CustomObject customObject) {
        switch (method) {
            case "insert_album_locally":
                localAlbumInterfaceCallback.onSaveAlbumLocally();
                break;
            case "delete_album_locally":
                localAlbumInterfaceCallback.onDeleteAlbumLocally();
                break;
            case "check_album_locally":
                localAlbumInterfaceCallback.onCheckAlbumExistence(customObject.getRawsCount());
                break;
            case "select_albums_locally":
                localAlbumInterfaceCallback.onSelectAlbumsResponse(customObject.getLocalAlbums());
                break;
        }
        super.onPostExecute(customObject);
    }

    @Override
    protected CustomObject doInBackground(Void... voids) {
        LocalAlbumService localAlbumService = new LocalAlbumService();
        switch (method) {
            case "insert_album_locally":
                localAlbumService.insertLocalAlbum(context, localAlbum);
                break;
            case "delete_album_locally":
                localAlbumService.deleteLocalAlbum(context, localAlbum.getName());
                break;
            case "check_album_locally":
                return new CustomObject(localAlbumService.checkLocalAlbumExistence(context, localAlbum.getName()));
            case "select_albums_locally":
                return new CustomObject(localAlbumService.getAllLocalAlbums(context));
        }
        return null;
    }

    public interface LocalAlbumInterface {
        void onSaveAlbumLocally();
        void onDeleteAlbumLocally();
        void onCheckAlbumExistence(Long s);
        void onSelectAlbumsResponse(ArrayList<LocalAlbum> localAlbums);
    }

    /**
     * used CustomObject to return different types from AsyncTask
     */
    public class CustomObject {
        private Long rawsCount;
        private ArrayList<LocalAlbum> localAlbums;

        public CustomObject(Long rawsCount) {
            this.rawsCount = rawsCount;
        }

        public CustomObject(ArrayList<LocalAlbum> localAlbums) {
            this.localAlbums = localAlbums;
        }

        public Long getRawsCount() {
            return rawsCount;
        }

        public ArrayList<LocalAlbum> getLocalAlbums() {
            return localAlbums;
        }
    }
}
