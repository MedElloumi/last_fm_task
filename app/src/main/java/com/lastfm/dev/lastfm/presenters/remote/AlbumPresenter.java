package com.lastfm.dev.lastfm.presenters.remote;

import com.lastfm.dev.lastfm.services.remote.AlbumService;
import com.lastfm.dev.lastfm.configuration.Settings;
import com.lastfm.dev.lastfm.configuration.RetrofitClient;
import com.lastfm.dev.lastfm.models.TopAlbumsResponse.TopAlbumsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlbumPresenter {
    private AlbumService albumService = RetrofitClient.getRetrofit().create(AlbumService.class);

    private String mbid;
    private AlbumInterface albumInterfaceCallback;

    public AlbumPresenter(AlbumInterface albumInterfaceCallback, String mbid){
        this.albumInterfaceCallback = albumInterfaceCallback;
        this.mbid = mbid;
    }

    public void returnAlbumsList() {
        Call <TopAlbumsResponse> call = albumService.getTopAlbumsByMbid(mbid, Settings.albumsSearchParameter, Settings.jsonFormat, Settings.apiKey);
        call.enqueue(new Callback<TopAlbumsResponse>() {

            @Override
            public void onResponse(Call<TopAlbumsResponse> call, Response<TopAlbumsResponse> response) {
                if (response.isSuccessful()) {
                    albumInterfaceCallback.onAlbumResponse(response);
                }
            }

            @Override
            public void onFailure(Call<TopAlbumsResponse> call, Throwable t) {
                albumInterfaceCallback.onAlbumFailure(t);
            }
        });
    }

    public interface AlbumInterface {
        void onAlbumResponse(Response<TopAlbumsResponse> response);
        void onAlbumFailure(Throwable t);
    }
}




