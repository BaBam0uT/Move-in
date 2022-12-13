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
    @Query("SELECT * FROM movieentity")
    suspend fun getFavMovies(): List<MovieEntity>

    @Query("SELECT * FROM serieentity")
    suspend fun getFavSeries(): List<SerieEntity>

    @Query("SELECT * FROM actorentity")
    suspend fun getFavActors(): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(film: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: SerieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActorEntity)

    @Query("DELETE FROM movieentity WHERE id = :id")
    suspend fun deleteMovie(id: String)

    @Query("DELETE FROM serieentity WHERE id = :id")
    suspend fun deleteSerie(id: String)

    @Query("DELETE FROM actorentity WHERE id = :id")
    suspend fun deleteActor(id: String)
}