package com.example.movein.database

import androidx.room.*
import com.example.movein.converters.Converters
import com.example.movein.entity.ActorEntity
import com.example.movein.entity.MovieEntity
import com.example.movein.entity.SerieEntity

@Database(entities = [MovieEntity::class, SerieEntity::class, ActorEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): MovieDao
}