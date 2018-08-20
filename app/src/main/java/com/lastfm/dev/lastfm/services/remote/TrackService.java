package com.lastfm.dev.lastfm.services.remote;

import com.lastfm.dev.lastfm.models.TopTracksResponse.TopTracksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohamed Elloumi on 18.08.18.
 */
public interface TrackService {
    @GET("?")
    Call<TopTracksResponse> getTopTrackByMbid(@Query("mbid") String mbid,
                                             @Query("method") String method,
                                             @Query("format") String format,
                                             @Query("api_key") String apiKey);
}
