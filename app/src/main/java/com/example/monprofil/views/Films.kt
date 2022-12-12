package com.example.monprofil.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.monprofil.R
import com.example.monprofil.entity.FilmEntity
import com.example.monprofil.models.TmdbMovie
import com.example.monprofil.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Films(
    classes: WindowSizeClass,
    viewmodel: MainViewModel,
    navController: NavHostController
) {
    val items = listOf(
        Screen("movies", painterResource(id = R.drawable.movies), "Movies Icon", "Movies"),
        Screen("series", painterResource(id = R.drawable.series), "Series Icon", "Series"),
        Screen("actors", painterResource(id = R.drawable.actors), "Actors Icon", "Actors"
        )
    )
    val films by viewmodel.movies.collectAsState()
    viewmodel.getFilmsInitiaux()
    val searchWidgetState by viewmodel.searchWidgetState
    val searchTextState by viewmodel.searchTextState
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when (classes.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "un film",
                        onTextChange = {
                            viewmodel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                            viewmodel.getSearchFilms()
                        },
                        onSearchTriggered = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        viewmodel = viewmodel
                    )
                },
                bottomBar = {
                    BottomNavigation ( backgroundColor = colorResource(R.color.purple_700) ) {
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.resourceId, contentDescription = screen.description, tint = Color.White) },
                                selectedContentColor = Color.White,
                                unselectedContentColor = Color.Black,
                                label = { Text(screen.label) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    viewmodel.isFavList = false
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                    modifier = Modifier.background(Color.Black),) {
                    items(films) { film ->
                        film.poster_path?.let { it1 ->
                            MovieCard(viewmodel = viewmodel,
                                navController = navController,
                                film = film,
                                path = it1
                            )
                        }
                    }
                }
            }
        }
        else -> {
            Row {
                NavigationRail ( backgroundColor = colorResource(R.color.purple_700) ) {
                    items.forEach { screen ->
                        NavigationRailItem(
                            icon = { Icon(screen.resourceId, contentDescription = screen.description, tint = Color.White) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Black,
                            onClick = {
                                viewmodel.isFavList = false
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
                Column() {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "un film",
                        onTextChange = {
                            viewmodel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                            viewmodel.getSearchFilms()
                        },
                        onSearchTriggered = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        viewmodel = viewmodel
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.background(Color.Black),
                    ) {
                        items(films) { film ->
                            film.backdrop_path?.let {
                                MovieCard(viewmodel = viewmodel,
                                    navController = navController,
                                    film = film,
                                    path = it
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(viewmodel: MainViewModel, navController: NavHostController, film: TmdbMovie, path: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White)
            .padding(5.dp)
            .clickable { navController.navigate("detailsFilm/${film.id}") },
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500$path",
                contentDescription = "Affiche du film"
            )
            FavoriteMovieIcon(viewmodel = viewmodel, film = film)
        }
        Text(text = film.title, color = Color.Black)
        Text(text = film.release_date, color = Color.Black)
    }
}

@Composable
fun FavoriteMovieIcon(viewmodel: MainViewModel, film: TmdbMovie) {
    IconButton(onClick = {
        if(film.isFav) {
            viewmodel.deleteFavMovie(film.id)
            if(viewmodel.isFavList) {
                viewmodel.getFavMovies()
            } else {
                viewmodel.getFilmsInitiaux()
            }
        } else {
            viewmodel.addFavMovie(FilmEntity(fiche = film, id = film.id))
            if(viewmodel.isFavList) {
                viewmodel.getFavMovies()
            } else {
                viewmodel.getFilmsInitiaux()
            }
        }
    }) {
        Icon(imageVector = Icons.Outlined.Favorite,
            contentDescription = "Favorite Icon",
            tint = if (film.isFav) Color.Red else Color.White,
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp))
    }
}