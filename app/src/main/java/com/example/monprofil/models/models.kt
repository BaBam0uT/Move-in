package com.example.monprofil.models

data class TmdbMovieResult(
    val results: List<TmdbMovie> = listOf())

data class TmdbSerieResult(
    val results: List<TmdbSerie> = listOf())

data class TmdbActorResult(
    val results: List<TmdbActor> = listOf())

data class TmdbMovie(
    var overview: String = "",
    val release_date: String = "",
    val id: String = "",
    val title: String = "",
    val genres: List<Genre> = listOf(),
    val backdrop_path: String? = "",
    val poster_path: String? = "",
    val credits: Credits = Credits(),
    val isFav: Boolean = false
)

data class TmdbActor(
    val id: String = "",
    val profile_path: String = "",
    val name: String = "",
    val isFav: Boolean = false
)

data class TmdbSerie(
    val id: String = "",
    val backdrop_path: String = "",
    val credits: Credits = Credits(),
    val first_air_date: String = "",
    val genres: List<Genre> = listOf(),
    val origin_country: List<String> = listOf(),
    val name: String = "",
    val overview: String = "",
    val poster_path: String = "",
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
