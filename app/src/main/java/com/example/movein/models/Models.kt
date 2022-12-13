package com.example.movein.models

data class TmdbMovieResults(
    val results: List<TmdbMovie> = listOf())

data class TmdbSerieResults(
    val results: List<TmdbSerie> = listOf())

data class TmdbActorResults(
    val results: List<TmdbActor> = listOf())

data class TmdbMovie(
    val id: String = "",
    val title: String = "",
    var overview: String = "",
    val backdrop_path: String? = "",
    val poster_path: String? = "",
    val release_date: String = "",
    val genres: List<Genre> = listOf(),
    val credits: Credits = Credits(),
    val isFav: Boolean = false
)

data class TmdbActor(
    val id: String = "",
    val name: String = "",
    val profile_path: String? = "",
    val isFav: Boolean = false
)

data class TmdbSerie(
    val id: String = "",
    val name: String = "",
    val overview: String = "",
    val poster_path: String? = "",
    val backdrop_path: String? = "",
    val first_air_date: String = "",
    val genres: List<Genre> = listOf(),
    val credits: Credits = Credits(),
    val isFav: Boolean = false
)

data class Credits(
    val cast: List<Cast> = listOf(),
)

data class Genre(
    val name: String = ""
)

data class Cast(
    val character: String = "",
    val name: String = "",
    val profile_path: String = ""
)
