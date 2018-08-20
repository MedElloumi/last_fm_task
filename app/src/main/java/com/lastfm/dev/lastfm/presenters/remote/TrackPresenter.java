package com.lastfm.dev.lastfm.presenters.remote;

import com.lastfm.dev.lastfm.services.remote.TrackService;
import com.lastfm.dev.lastfm.configuration.RetrofitClient;
import com.lastfm.dev.lastfm.configuration.Settings;
import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracksResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public class TrackPresenter {
    private TracksInterface tracksInterface;
    private String mbid;
    private TrackService trackService = RetrofitClient.getRetrofit().create(TrackService.class);

    public TrackPresenter(TracksInterface tracksInterface, String mbid){
        this.tracksInterface = tracksInterface;
        this.mbid = mbid;
    }

    public void returnTopTracks() {
        Call<TopTracksResponse> call = trackService.getTopTrackByMbid(mbid, Settings.tracksSearchParameter, Settings.jsonFormat, Settings.apiKey);
        call.enqueue(new Callback<TopTracksResponse>() {
            @Override
            public void onResponse(Call<TopTracksResponse> call, Response<TopTracksResponse> response) {
                if (response.isSuccessful()) {
                    tracksInterface.onTracksResponse(response);
                }
            }

            @Override
            public void onFailure(Call<TopTracksResponse> call, Throwable t) {
                tracksInterface.onTracksFailure(t);
            }
        });
    }

    public interface TracksInterface{
        void onTracksResponse(Response<TopTracksResponse> response);
        void onTracksFailure(Throwable t);
    }

}
