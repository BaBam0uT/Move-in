package com.example.monprofil.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monprofil.SearchWidgetState
import com.example.monprofil.directories.TmdbAPI
import com.example.monprofil.models.TmdbActor
import com.example.monprofil.models.TmdbMovie
import com.example.monprofil.models.TmdbSerie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val service = retrofit.create(TmdbAPI::class.java)
    val movie = MutableStateFlow(TmdbMovie())
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val serie = MutableStateFlow(TmdbSerie())
    val series = MutableStateFlow<List<TmdbSerie>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    val API_KEY = "f35478e744bbf37fb49a1635fe867c3a"

    fun getFilmsInitiaux() {
        viewModelScope.launch {
            movies.value = service.lastmovies(API_KEY).results
        }
    }

    fun getSearchFilms() {
        viewModelScope.launch {
            movies.value = service.searchmovies(API_KEY, searchTextState.value).results
        }
    }

    fun getSearchSeries() {
        viewModelScope.launch {
            series.value = service.searchSeries(API_KEY, searchTextState.value).results
        }
    }

    fun getSearchActors() {
        viewModelScope.launch {
            actors.value = service.searchActors(API_KEY, searchTextState.value).results
        }
    }

    fun getSeriesInitiaux() {
        viewModelScope.launch {
            series.value = service.lastTv(API_KEY).results
        }
    }

    fun getActorsInitiaux() {
        viewModelScope.launch {
            actors.value = service.lastPerson(API_KEY).results
        }
    }

    fun getFilmsDetails(idFilm: String) {
        viewModelScope.launch {
            movie.value = service.movieDetails(idFilm, API_KEY)
        }
    }

    fun getSeriesDetails(idSerie: String) {
        viewModelScope.launch {
            serie.value = service.serieDetails(idSerie, API_KEY)
        }
    }

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}