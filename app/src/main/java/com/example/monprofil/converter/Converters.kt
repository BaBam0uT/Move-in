package com.example.monprofil.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.monprofil.models.TmdbActor
import com.example.monprofil.models.TmdbMovie
import com.example.monprofil.models.TmdbSerie
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class Converters() {
    val moshi = Moshi.Builder().build()
    val filmJsonadapterFilm = moshi.adapter(TmdbMovie::class.java)
    val filmJsonadapterSerie = moshi.adapter(TmdbSerie::class.java)
    val filmJsonadapterActor = moshi.adapter(TmdbActor::class.java)

    @TypeConverter
    fun StringToTmdbMovie(value: String): TmdbMovie? {
        return filmJsonadapterFilm.fromJson(value)
    }

    @TypeConverter
    fun TmdbMovieToString(film: TmdbMovie): String {
        return filmJsonadapterFilm.toJson(film)
    }

    @TypeConverter
    fun StringToTmdbSerie(value: String): TmdbSerie? {
        return filmJsonadapterSerie.fromJson(value)
    }

    @TypeConverter
    fun TmdbSerieToString(serie: TmdbSerie): String {
        return filmJsonadapterSerie.toJson(serie)
    }

    @TypeConverter
    fun StringToTmdbActor(value: String): TmdbActor? {
        return filmJsonadapterActor.fromJson(value)
    }

    @TypeConverter
    fun TmdbMovieToString(actor: TmdbActor): String {
        return filmJsonadapterActor.toJson(actor)
    }
}