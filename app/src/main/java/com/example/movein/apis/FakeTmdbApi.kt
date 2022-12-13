package com.example.movein.apis

import com.example.movein.models.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.http.Query

class FakeTmdbApi : TmdbApi {
    private val moshi: Moshi = Moshi.Builder().build()
    // Fake Json variables
    private val jsonAdapterMovies: JsonAdapter<TmdbMovieResults> = moshi.adapter(TmdbMovieResults::class.java)
    private val jsonAdapterSeries: JsonAdapter<TmdbSerieResults> = moshi.adapter(TmdbSerieResults::class.java)
    private val jsonAdapterActors: JsonAdapter<TmdbActorResults> = moshi.adapter(TmdbActorResults::class.java)
    private val jsonAdapterMovie: JsonAdapter<TmdbMovie> = moshi.adapter(TmdbMovie::class.java)
    private val jsonAdapterSerie: JsonAdapter<TmdbSerie> = moshi.adapter(TmdbSerie::class.java)

    // Fake Json methods
    private val jsonResults = "{\"page\":1,\"results\":[{\"adult\":false, ... "

    override suspend fun trendingMovies(@Query("api_key") api_key: String): TmdbMovieResults {
        return jsonAdapterMovies.fromJson(jsonResults) ?: TmdbMovieResults()
    }

    override suspend fun trendingSeries(@Query("api_key") api_key: String): TmdbSerieResults {
        return jsonAdapterSeries.fromJson(jsonResults) ?: TmdbSerieResults()
    }

    override suspend fun trendingActors(@Query("api_key") api_key: String): TmdbActorResults {
        return jsonAdapterActors.fromJson(jsonResults) ?: TmdbActorResults()
    }

    override suspend fun searchMovies(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbMovieResults {
        return jsonAdapterMovies.fromJson(jsonResults) ?: TmdbMovieResults()
    }

    override suspend fun searchSeries(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbSerieResults {
        return jsonAdapterSeries.fromJson(jsonResults) ?: TmdbSerieResults()
    }

    override suspend fun searchActors(@Query("api_key") api_key: String, @Query("query") searchtext: String): TmdbActorResults {
        return jsonAdapterActors.fromJson(jsonResults) ?: TmdbActorResults()
    }

    override suspend fun movieDetails(
        id: String,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response: String
    ): TmdbMovie {
        return jsonAdapterMovie.fromJson(jsonResults) ?: TmdbMovie()
    }

    override suspend fun serieDetails(
        id: String,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response: String
    ): TmdbSerie {
        return jsonAdapterSerie.fromJson(jsonResults) ?: TmdbSerie()
    }
}