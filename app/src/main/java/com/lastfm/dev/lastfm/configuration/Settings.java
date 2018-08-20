package com.lastfm.dev.lastfm.configuration;

public class Settings {
    public static String baseUrl = "https://ws.audioscrobbler.com/2.0/";

    // search parameters used by the api &method=?
    public static String artistSearchParameter = "artist.search";
    public static String albumsSearchParameter = "artist.gettopalbums";
    public static String tracksSearchParameter = "artist.gettoptracks";

    public static String jsonFormat = "json";
    public static String apiKey = "0c5fcaaaea67d87217c67983151dca23";
}
