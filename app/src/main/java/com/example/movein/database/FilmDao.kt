package com.example.movein.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movein.entity.ActeurEntity
import com.example.movein.entity.FilmEntity
import com.example.movein.entity.SerieEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM filmentity")
    suspend fun getFavFilms(): List<FilmEntity>

    @Query("SELECT * FROM serieentity")
    suspend fun getFavSeries(): List<SerieEntity>

    @Query("SELECT * FROM acteurentity")
    suspend fun getFavActeurs(): List<ActeurEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: SerieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActeurEntity)

    @Query("DELETE FROM filmentity WHERE id = :id")
    suspend fun deleteFilm(id: String)

    @Query("DELETE FROM serieentity WHERE id = :id")
    suspend fun deleteSerie(id: String)

    @Query("DELETE FROM acteurentity WHERE id = :id")
    suspend fun deleteActeur(id: String)
}