package com.lastfm.dev.lastfm.presenters.remote;

import com.lastfm.dev.lastfm.services.remote.ArtistService;
import com.lastfm.dev.lastfm.configuration.Settings;
import com.lastfm.dev.lastfm.configuration.RetrofitClient;
import com.lastfm.dev.lastfm.models.ArtistSearchResponse.ArtistSearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArtistPresenter {
    private ArtistService artistService = RetrofitClient.getRetrofit().create(ArtistService.class);

    private String artistName;
    private ArtistInterface artistInterfaceCallBack;

    public ArtistPresenter(ArtistInterface artistInterfaceCallBack, String artistName){
        this.artistInterfaceCallBack = artistInterfaceCallBack;
        this.artistName = artistName;
    }

    public void returnArtistsList() {
        Call <ArtistSearchResponse> call = artistService.getArtistsByName(artistName, Settings.artistSearchParameter, Settings.jsonFormat, Settings.apiKey);
        call.enqueue(new Callback<ArtistSearchResponse>() {

            @Override
            public void onResponse(Call<ArtistSearchResponse> call, Response<ArtistSearchResponse> response) {
                if (response.isSuccessful()) {
                    artistInterfaceCallBack.onArtistResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ArtistSearchResponse> call, Throwable t) {
                artistInterfaceCallBack.onArtistFailure(t);
            }
        });
    }

    public interface ArtistInterface {
        void onArtistResponse(Response<ArtistSearchResponse> response);
        void onArtistFailure(Throwable t);
    }
}




