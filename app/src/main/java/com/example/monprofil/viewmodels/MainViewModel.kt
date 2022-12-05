package com.example.monprofil.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.monprofil.SearchWidgetState
import com.example.monprofil.entity.ActeurEntity
import com.example.monprofil.entity.FilmEntity
import com.example.monprofil.entity.SerieEntity
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
            movies.value = repo.lastmovies(API_KEY).results
        }
    }

    fun getSearchFilms() {
        viewModelScope.launch {
            movies.value = repo.searchmovies(API_KEY, searchTextState.value).results
        }
    }

    fun getSearchSeries() {
        viewModelScope.launch {
            series.value = repo.searchSeries(API_KEY, searchTextState.value).results
        }
    }

    fun getSearchActors() {
        viewModelScope.launch {
            actors.value = repo.searchActors(API_KEY, searchTextState.value).results
        }
    }

    fun getSeriesInitiaux() {
        viewModelScope.launch {
            series.value = repo.lastTv(API_KEY).results
        }
    }

    fun getActorsInitiaux() {
        viewModelScope.launch {
            actors.value = repo.lastPerson(API_KEY).results
        }
    }

    fun getFilmsDetails(idFilm: String) {
        viewModelScope.launch {
            movie.value = repo.movieDetails(idFilm, API_KEY, "credits")
        }
    }

    fun getSeriesDetails(idSerie: String) {
        viewModelScope.launch {
            serie.value = repo.serieDetails(idSerie, API_KEY, "credits")
        }
    }

    fun addFavMovie(movie: FilmEntity) {
        viewModelScope.launch {
            repo.insertFilm(movie)
        }
    }

    fun addFavSerie(serie: SerieEntity) {
        viewModelScope.launch {
            repo.insertSerie(serie)
        }
    }

    fun addFavActor(actor: ActeurEntity) {
        viewModelScope.launch {
            repo.insertActor(actor)
        }
    }

    fun getFavMovies() {
        viewModelScope.launch {
            repo.getFavFilms()
        }
    }

    fun getFavSeries() {
        viewModelScope.launch {
            repo.getFavSeries()
        }
    }

    fun getFavActors() {
        viewModelScope.launch {
            repo.getFavActeurs()
        }
    }

    fun deleteFavMovie(idMovie: String) {
        viewModelScope.launch {
            repo.deleteFilm(idMovie)
        }
    }

    fun deleteFavSerie(idSerie: String) {
        viewModelScope.launch {
            repo.deleteSerie(idSerie)
        }
    }

    fun deleteFavActor(idActor: String) {
        viewModelScope.launch {
            repo.deleteActeur(idActor)
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