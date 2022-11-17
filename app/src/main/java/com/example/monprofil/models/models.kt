package com.example.monprofil.models

class Genre(val id: Int = 0, val name:String = "")

class TmdbMovieResult(
    var page: Int = 0,
    val results: List<TmdbMovie> = listOf())

class TmdbSerieResult(
    var page: Int = 0,
    val results: List<TmdbSerie> = listOf())

class TmdbActorResult(
    var page: Int = 0,
    val results: List<TmdbActor> = listOf())

class TmdbMovie(
    var overview: String = "",
    val release_date: String = "",
    val id: String = "",
    val title: String = "",
    val genres: List<Genre> = listOf(),
    val backdrop_path: String? = "",
    val poster_path: String? = "")

class TmdbSerie(
    var overview: String = "",
    val first_air_date: String = "",
    val id: String = "",
    val name: String = "",
    val original_name: String = "",
    val backdrop_path: String? = "",
    val genres: List<Genre> = listOf(),
    val poster_path: String? = "")

class TmdbActor(
    val profile_path: String = "",
    val id: String = "",
    val name: String = "",
    val original_name: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "")