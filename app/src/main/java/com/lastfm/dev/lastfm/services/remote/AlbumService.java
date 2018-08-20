package com.lastfm.dev.lastfm.services.remote;

import com.lastfm.dev.lastfm.models.TopAlbumsResponse.TopAlbumsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohamed Elloumi on 17.08.18.
 */
public interface AlbumService {
    @GET("?")
    Call<TopAlbumsResponse> getTopAlbumsByMbid(@Query("mbid") String mbid,
                                               @Query("method") String method,
                                               @Query("format") String format,
                                               @Query("api_key") String apiKey);
}
