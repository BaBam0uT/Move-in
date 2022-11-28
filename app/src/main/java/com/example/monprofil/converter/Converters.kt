package com.example.monprofil.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.monprofil.models.TmdbMovie
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class Converters() {
    val moshi = Moshi.Builder().build()
    val filmJsonadapter = moshi.adapter(TmdbMovie::class.java)

    @TypeConverter
    fun StringToTmdbMovie(value: String): TmdbMovie? {
        return filmJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbMovieToString(film: TmdbMovie): String {
        return filmJsonadapter.toJson(film)
    }
}