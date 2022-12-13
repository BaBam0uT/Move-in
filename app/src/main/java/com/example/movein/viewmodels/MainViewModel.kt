package com.example.movein.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movein.views.SearchWidgetState
import com.example.movein.entity.ActeurEntity
import com.example.movein.entity.FilmEntity
import com.example.movein.entity.SerieEntity
import com.example.movein.models.TmdbActor
import com.example.movein.models.TmdbMovie
import com.example.movein.models.TmdbSerie
import com.example.movein.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val movie = MutableStateFlow(TmdbMovie())
    var movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val serie = MutableStateFlow(TmdbSerie())
    val series = MutableStateFlow<List<TmdbSerie>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    val API_KEY = "f35478e744bbf37fb49a1635fe867c3a"
    var isFavList = false;
    fun getFilmsInitiaux() {
        viewModelScope.launch {
            val moviesFav = repo.getFavFilms()
            movies.value = repo.lastmovies(API_KEY).results.map { film ->
                if (moviesFav.contains(moviesFav.find{element -> element.fiche == film })) film.copy(isFav = true) else film
            }
        }
    }

    fun getSeriesInitiaux() {
        viewModelScope.launch {
            val seriesFav = repo.getFavSeries()
            series.value = repo.lastTv(API_KEY).results.map { serie ->
                if (seriesFav.contains(seriesFav.find{element -> element.fiche == serie })) serie.copy(isFav = true) else serie
            }
        }
    }

    fun getActorsInitiaux() {
        viewModelScope.launch {
            val actorsFav = repo.getFavActeurs()
            actors.value = repo.lastPerson(API_KEY).results.map { actor ->
                if (actorsFav.contains(actorsFav.find{element -> element.fiche == actor })) actor.copy(isFav = true) else actor
            }
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
            movies.value = listOf()
            repo.getFavFilms().map {
                movies.value += it.fiche.copy(isFav = true)
            }
        }
    }

    fun getFavSeries() {
        viewModelScope.launch {
            series.value = listOf()
            repo.getFavSeries().map {
                series.value += it.fiche.copy(isFav = true)
            }
        }
    }

    fun getFavActors() {
        viewModelScope.launch {
            actors.value = listOf()
            repo.getFavActeurs().map {
                actors.value += it.fiche.copy(isFav = true)
            }
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