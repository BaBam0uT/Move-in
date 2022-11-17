package com.example.monprofil.directories

import androidx.compose.runtime.State
import com.example.monprofil.models.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week?")
    suspend fun lastmovies(@Query("api_key") api_key: String) : TmdbMovieResult

    @GET("trending/tv/week?")
    suspend fun lastTv(@Query("api_key") api_key: String) : TmdbSerieResult

    @GET("trending/person/week?")
    suspend fun lastPerson(@Query("api_key") api_key: String) : TmdbActorResult

    @GET("search/movie")
    suspend fun searchmovies(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbMovieResult

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbSerieResult

    @GET("search/person")
    suspend fun searchActors(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String) : TmdbActorResult

    @GET("movie/{idFilm}")
    suspend fun movieDetails(@Path("idFilm") id: String, @Query("api_key") api_key: String): TmdbMovie

    @GET("tv/{idSerie}")
    suspend fun serieDetails(@Path("idSerie") id: String, @Query("api_key") api_key: String): TmdbSerie

}

