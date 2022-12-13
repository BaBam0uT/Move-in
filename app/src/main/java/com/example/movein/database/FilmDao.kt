package com.example.movein.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movein.entity.ActorEntity
import com.example.movein.entity.MovieEntity
import com.example.movein.entity.SerieEntity

@Dao
interface FilmDao {
    // Get favorite movies from database
    @Query("SELECT * FROM movieentity")
    suspend fun getFavMovies(): List<MovieEntity>

    // Get favorite series from database
    @Query("SELECT * FROM serieentity")
    suspend fun getFavSeries(): List<SerieEntity>

    // Get favorite actors from database
    @Query("SELECT * FROM actorentity")
    suspend fun getFavActors(): List<ActorEntity>

    // Insert favorite movie on database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(film: MovieEntity)

    // Insert favorite serie on database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: SerieEntity)

    // Insert favorite actor on database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActorEntity)

    // Delete favorite movie in database
    @Query("DELETE FROM movieentity WHERE id = :id")
    suspend fun deleteMovie(id: String)

    // Delete favorite serie in database
    @Query("DELETE FROM serieentity WHERE id = :id")
    suspend fun deleteSerie(id: String)

    // Delete favorite actor in database
    @Query("DELETE FROM actorentity WHERE id = :id")
    suspend fun deleteActor(id: String)
}