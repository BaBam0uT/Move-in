package com.example.monprofil

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.monprofil.converter.Converters
import com.example.monprofil.database.AppDatabase
import com.example.monprofil.database.FilmDao
import com.example.monprofil.repository.FakeTmdbApi
import com.example.monprofil.repository.Repository
import com.example.monprofil.repository.TmdbAPI
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@HiltAndroidApp
class MyApplication: Application()

@Qualifier
annotation class FakeApi
@Qualifier
annotation class RealApi

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providerConverters() : Converters {
        val moshi = Moshi.Builder().build()
        return Converters()
    }
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context)
            : FilmDao =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "move_in_database"
        )
            .fallbackToDestructiveMigration()
            .build().filmDao()

    @RealApi
    @Singleton
    @Provides
    fun provideTmdbApi() : TmdbAPI =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(TmdbAPI::class.java)

    @FakeApi
    @Singleton
    @Provides
    fun provideFakeTmdbApi() : TmdbAPI { return FakeTmdbApi() }

    @Singleton
    @Provides
    fun provideRepository(@FakeApi api: TmdbAPI, db: FilmDao) = Repository(api, db)
}