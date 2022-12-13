package com.example.movein.apis

import com.example.movein.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    // Get Trending Movies
    @GET("trending/movie/week?")
    suspend fun trendingMovies(@Query("api_key") api_key: String) : TmdbMovieResults

    // Get Trending TV Series
    @GET("trending/tv/week?")
    suspend fun trendingSeries(@Query("api_key") api_key: String) : TmdbSerieResults

    // Get Trending Actors
    @GET("trending/person/week?")
    suspend fun trendingActors(@Query("api_key") api_key: String) : TmdbActorResults

    // Get Movies Searched
    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbMovieResults

    // Get TV Series Searched
    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbSerieResults

    // Get Actors Searched
    @GET("search/person")
    suspend fun searchActors(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbActorResults

    // Get Movie Details
    @GET("movie/{idMovie}")
    suspend fun movieDetails(@Path("idMovie") id: String, @Query("api_key") api_key: String, @Query("append_to_response") append_to_response: String): TmdbMovie

    // Get Serie Details
    @GET("tv/{idSerie}")
    suspend fun serieDetails(@Path("idSerie") id: String, @Query("api_key") api_key: String, @Query("append_to_response") append_to_response: String): TmdbSerie

}

