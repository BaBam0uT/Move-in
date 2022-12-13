package com.example.movein.apis

import com.example.movein.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/week?")
    suspend fun trendingMovies(@Query("api_key") api_key: String) : TmdbMovieResults

    @GET("trending/tv/week?")
    suspend fun trendingSeries(@Query("api_key") api_key: String) : TmdbSerieResults

    @GET("trending/person/week?")
    suspend fun trendingActors(@Query("api_key") api_key: String) : TmdbActorResults

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbMovieResults

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbSerieResults

    @GET("search/person")
    suspend fun searchActors(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbActorResults

    @GET("movie/{idFilm}")
    suspend fun movieDetails(@Path("idFilm") id: String, @Query("api_key") api_key: String, @Query("append_to_response") append_to_response: String): TmdbMovie

    @GET("tv/{idSerie}")
    suspend fun serieDetails(@Path("idSerie") id: String, @Query("api_key") api_key: String, @Query("append_to_response") append_to_response: String): TmdbSerie

}

