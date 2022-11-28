package com.example.monprofil.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.monprofil.SearchWidgetState
import com.example.monprofil.models.TmdbActor
import com.example.monprofil.models.TmdbMovie
import com.example.monprofil.models.TmdbSerie
import com.example.monprofil.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val movie = MutableStateFlow(TmdbMovie())
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val serie = MutableStateFlow(TmdbSerie())
    val series = MutableStateFlow<List<TmdbSerie>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    val API_KEY = "f35478e744bbf37fb49a1635fe867c3a"

    fun getFilmsInitiaux() {
        viewModelScope.launch {
            movies.value = repo.api.lastmovies(API_KEY).results
        }
    }

    fun getSearchFilms() {
        viewModelScope.launch {
            movies.value = repo.api.searchmovies(API_KEY, searchTextState.value).results
        }
    }

    fun getSearchSeries() {
        viewModelScope.launch {
            series.value = repo.api.searchSeries(API_KEY, searchTextState.value).results
        }
    }

    fun getSearchActors() {
        viewModelScope.launch {
            actors.value = repo.api.searchActors(API_KEY, searchTextState.value).results
        }
    }

    fun getSeriesInitiaux() {
        viewModelScope.launch {
            series.value = repo.api.lastTv(API_KEY).results
        }
    }

    fun getActorsInitiaux() {
        viewModelScope.launch {
            actors.value = repo.api.lastPerson(API_KEY).results
        }
    }

    fun getFilmsDetails(idFilm: String) {
        viewModelScope.launch {
            movie.value = repo.api.movieDetails(idFilm, API_KEY, "credits")
        }
    }

    fun getSeriesDetails(idSerie: String) {
        viewModelScope.launch {
            serie.value = repo.api.serieDetails(idSerie, API_KEY, "credits")
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