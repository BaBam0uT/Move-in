package com.example.movein.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movein.views.SearchWidgetState
import com.example.movein.entity.ActorEntity
import com.example.movein.entity.MovieEntity
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
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movie = MutableStateFlow(TmdbMovie())
    val series = MutableStateFlow<List<TmdbSerie>>(listOf())
    val serie = MutableStateFlow(TmdbSerie())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    var isFavList = false

    companion object {
        const val APIKEY = "f35478e744bbf37fb49a1635fe867c3a"
        const val CREDITS = "credits"
    }
    
    fun getTrendingMovies() {
        viewModelScope.launch {
            val moviesFav = repository.getFavMovies()
            movies.value = repository.trendingMovies(APIKEY).results.map { film ->
                if (isFavMovie(moviesFav, film)) film.copy(isFav = true) else film
            }
        }
    }

    fun getTrendingSeries() {
        viewModelScope.launch {
            val seriesFav = repository.getFavSeries()
            series.value = repository.trendingSeries(APIKEY).results.map { serie ->
                if (isFavSerie(seriesFav, serie)) serie.copy(isFav = true) else serie
            }
        }
    }

    fun getTrendingActors() {
        viewModelScope.launch {
            val actorsFav = repository.getFavActors()
            actors.value = repository.trendingActors(APIKEY).results.map { actor ->
                if (isFavActor(actorsFav, actor)) actor.copy(isFav = true) else actor
            }
        }
    }

    fun getSearchedMovies() {
        viewModelScope.launch {
            movies.value = repository.searchMovies(APIKEY, searchTextState.value).results
        }
    }

    fun getSearchedSeries() {
        viewModelScope.launch {
            series.value = repository.searchSeries(APIKEY, searchTextState.value).results
        }
    }

    fun getSearchedActors() {
        viewModelScope.launch {
            actors.value = repository.searchActors(APIKEY, searchTextState.value).results
        }
    }

    fun getFilmsDetails(idMovie: String) {
        viewModelScope.launch {
            movie.value = repository.movieDetails(idMovie, APIKEY, CREDITS)
        }
    }

    fun getSeriesDetails(idSerie: String) {
        viewModelScope.launch {
            serie.value = repository.serieDetails(idSerie, APIKEY, CREDITS)
        }
    }

    fun addFavMovie(movie: MovieEntity) {
        viewModelScope.launch {
            repository.insertMovie(movie)
        }
    }

    fun addFavSerie(serie: SerieEntity) {
        viewModelScope.launch {
            repository.insertSerie(serie)
        }
    }

    fun addFavActor(actor: ActorEntity) {
        viewModelScope.launch {
            repository.insertActor(actor)
        }
    }

    fun getFavMovies() {
        viewModelScope.launch {
            movies.value = listOf()
            repository.getFavMovies().map {
                movies.value += it.content.copy(isFav = true)
            }
        }
    }

    fun getFavSeries() {
        viewModelScope.launch {
            series.value = listOf()
            repository.getFavSeries().map {
                series.value += it.content.copy(isFav = true)
            }
        }
    }

    fun getFavActors() {
        viewModelScope.launch {
            actors.value = listOf()
            repository.getFavActors().map {
                actors.value += it.content.copy(isFav = true)
            }
        }
    }

    fun deleteFavMovie(idMovie: String) {
        viewModelScope.launch {
            repository.deleteMovie(idMovie)
        }
    }

    fun deleteFavSerie(idSerie: String) {
        viewModelScope.launch {
            repository.deleteSerie(idSerie)
        }
    }

    fun deleteFavActor(idActor: String) {
        viewModelScope.launch {
            repository.deleteActor(idActor)
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

    // Functions born from refactoring
    private fun isFavMovie(
        moviesFav: List<MovieEntity>,
        film: TmdbMovie
    ) = moviesFav.contains(moviesFav.find { element -> element.content == film })

    private fun isFavSerie(
        seriesFav: List<SerieEntity>,
        serie: TmdbSerie
    ) = seriesFav.contains(seriesFav.find { element -> element.content == serie })

    private fun isFavActor(
        actorsFav: List<ActorEntity>,
        actor: TmdbActor
    ) = actorsFav.contains(actorsFav.find { element -> element.content == actor })
}