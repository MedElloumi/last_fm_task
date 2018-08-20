package com.lastfm.dev.lastfm.services.remote;

import com.lastfm.dev.lastfm.models.ArtistSearchResponse.ArtistSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArtistService {
    @GET("?")
    Call <ArtistSearchResponse> getArtistsByName(@Query("artist") String artist,
                                                 @Query("method") String method,
                                                 @Query("format") String format,
                                                 @Query("api_key") String apiKey);
}
