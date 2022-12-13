package com.example.movein.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.movein.models.TmdbActor
import com.example.movein.models.TmdbMovie
import com.example.movein.models.TmdbSerie
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class Converters() {
    private val moshi: Moshi = Moshi.Builder().build()
    // Json adapters variables
    private val jsonAdapterMovie: JsonAdapter<TmdbMovie> = moshi.adapter(TmdbMovie::class.java)
    private val jsonAdapterSerie: JsonAdapter<TmdbSerie> = moshi.adapter(TmdbSerie::class.java)
    private val jsonAdapterActor: JsonAdapter<TmdbActor> = moshi.adapter(TmdbActor::class.java)

    // Converters Methods
    @TypeConverter
    fun stringToTmdbMovie(value: String): TmdbMovie? {
        return jsonAdapterMovie.fromJson(value)
    }

    @TypeConverter
    fun stringToTmdbSerie(value: String): TmdbSerie? {
        return jsonAdapterSerie.fromJson(value)
    }

    @TypeConverter
    fun stringToTmdbActor(value: String): TmdbActor? {
        return jsonAdapterActor.fromJson(value)
    }

    @TypeConverter
    fun tmdbMovieToString(film: TmdbMovie): String {
        return jsonAdapterMovie.toJson(film)
    }

    @TypeConverter
    fun tmdbSerieToString(serie: TmdbSerie): String {
        return jsonAdapterSerie.toJson(serie)
    }

    @TypeConverter
    fun tmdbActorToString(actor: TmdbActor): String {
        return jsonAdapterActor.toJson(actor)
    }
}