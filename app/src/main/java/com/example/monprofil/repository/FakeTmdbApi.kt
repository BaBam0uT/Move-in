package com.example.monprofil.repository

import com.example.monprofil.models.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.http.Query

class FakeTmdbApi : TmdbAPI {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapterMovies: JsonAdapter<TmdbMovieResult> = moshi.adapter(TmdbMovieResult::class.java)
    val jsonAdapterSeries: JsonAdapter<TmdbSerieResult> = moshi.adapter(TmdbSerieResult::class.java)
    val jsonAdapterActors: JsonAdapter<TmdbActorResult> = moshi.adapter(TmdbActorResult::class.java)
    val jsonAdapterMovie: JsonAdapter<TmdbMovie> = moshi.adapter(TmdbMovie::class.java)
    val jsonAdapterSerie: JsonAdapter<TmdbSerie> = moshi.adapter(TmdbSerie::class.java)

    // Faux Json
    val jsonresult = "{\"page\":1,\"results\":[{\"adult\":false, ... "

    override suspend fun lastmovies(@Query("api_key") api_key: String): TmdbMovieResult {
        val res = jsonAdapterMovies.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbMovieResult()
    }

    override suspend fun lastTv(@Query("api_key") api_key: String): TmdbSerieResult {
        val res = jsonAdapterSeries.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbSerieResult()
    }

    override suspend fun lastPerson(@Query("api_key") api_key: String): TmdbActorResult {
        val res = jsonAdapterActors.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbActorResult()
    }

    override suspend fun searchmovies(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbMovieResult {
        val res = jsonAdapterMovies.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbMovieResult()
    }

    override suspend fun searchSeries(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbSerieResult {
        val res = jsonAdapterSeries.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbSerieResult()
    }

    override suspend fun searchActors(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbActorResult {
        val res = jsonAdapterActors.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbActorResult()
    }

    override suspend fun movieDetails(
        id: String,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response: String
    ): TmdbMovie {
        val res = jsonAdapterMovie.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbMovie()
    }

    override suspend fun serieDetails(
        id: String,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response: String
    ): TmdbSerie {
        val res = jsonAdapterSerie.fromJson(jsonresult)
        if (res != null) return res
        else return TmdbSerie()
    }
}