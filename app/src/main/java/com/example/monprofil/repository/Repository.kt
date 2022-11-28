package com.example.monprofil.repository

import android.content.Context
import androidx.room.Room
import com.example.monprofil.converter.Converters
import com.example.monprofil.database.AppDatabase
import com.example.monprofil.database.FilmDao
import com.squareup.moshi.Moshi
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

class Repository(api: TmdbAPI, db: FilmDao) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val api = retrofit.create(TmdbAPI::class.java)
}
