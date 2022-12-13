package com.example.movein.repository

import com.example.movein.apis.TmdbApi
import com.example.movein.database.FilmDao
import com.example.movein.entity.ActorEntity
import com.example.movein.entity.MovieEntity
import com.example.movein.entity.SerieEntity

class Repository(private val api: TmdbApi, private val db: FilmDao) {
    // All methods that use TmdbApi
    suspend fun trendingMovies(apiKey: String) = api.trendingMovies(apiKey)
    suspend fun trendingSeries(apiKey: String) = api.trendingSeries(apiKey)
    suspend fun trendingActors(apiKey: String) = api.trendingActors(apiKey)
    suspend fun searchMovies(apiKey: String, searchText: String) = api.searchMovies(apiKey, searchText)
    suspend fun searchSeries(apiKey: String, searchText: String) = api.searchSeries(apiKey, searchText)
    suspend fun searchActors(apiKey: String, searchText: String) = api.searchActors(apiKey, searchText)
    suspend fun movieDetails(apiKey: String, idMovie: String, appendToResponse: String) = api.movieDetails(apiKey, idMovie, appendToResponse)
    suspend fun serieDetails(apiKey: String, idSerie: String, appendToResponse: String) = api.serieDetails(apiKey, idSerie, appendToResponse)

    // All methods that use the Database
    suspend fun getFavMovies() = db.getFavMovies()
    suspend fun getFavSeries() = db.getFavSeries()
    suspend fun getFavActors() = db.getFavActors()
    suspend fun insertMovie(movie: MovieEntity) = db.insertMovie(movie)
    suspend fun insertSerie(serie: SerieEntity) = db.insertSerie(serie)
    suspend fun insertActor(actor: ActorEntity) = db.insertActor(actor)
    suspend fun deleteMovie(idMovie: String) = db.deleteMovie(idMovie)
    suspend fun deleteSerie(idSerie: String) = db.deleteSerie(idSerie)
    suspend fun deleteActor(idActor: String) = db.deleteActor(idActor)
}
