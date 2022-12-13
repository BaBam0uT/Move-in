package com.example.movein.database

import androidx.room.*
import com.example.movein.converter.Converters
import com.example.movein.entity.ActeurEntity
import com.example.movein.entity.FilmEntity
import com.example.movein.entity.SerieEntity

@Database(entities = [FilmEntity::class, SerieEntity::class,
    ActeurEntity::class], version = 1)
// pensez bien à faire évoluer le numéro de version à chaque fois que vous changez
// le schéma de la base
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}