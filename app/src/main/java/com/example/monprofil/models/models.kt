package com.example.monprofil.models

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
    val original_title: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "")

class TmdbSerie(
    val first_air_date: String = "",
    val id: String = "",
    val name: String = "",
    val original_name: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "")

class TmdbActor(
    val profile_path: String = "",
    val id: String = "",
    val name: String = "",
    val original_name: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "")