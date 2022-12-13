package com.example.movein.views

import android.annotation.SuppressLint
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movein.entity.MovieEntity
import com.example.movein.models.TmdbMovie
import com.example.movein.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Movies(
    classes: WindowSizeClass,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val movies by viewModel.movies.collectAsState()
    viewModel.getTrendingMovies()
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState
    when (classes.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "a movie",
                        onTextChange = { viewModel.updateSearchTextState(newValue = it) },
                        onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                        onSearchClicked = { viewModel.getSearchedMovies() },
                        onSearchTriggered = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                        viewModel = viewModel
                    )
                },
                bottomBar = {
                    NavigationBar(viewModel = viewModel, navController = navController)
                }
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize(),) {
                    items(movies) { movie ->
                        MovieCard(viewModel = viewModel,
                            navController = navController,
                            movie = movie,
                            path = movie.poster_path
                        )
                    }
                }
            }
        }
        else -> {
            Row {
                NavigationRail(viewModel = viewModel, navController = navController)
                Column {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "a movie",
                        onTextChange = { viewModel.updateSearchTextState(newValue = it) },
                        onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                        onSearchClicked = { viewModel.getSearchedMovies() },
                        onSearchTriggered = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                        viewModel = viewModel
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize(),
                    ) {
                        items(movies) { movie ->
                            MovieCard(viewModel = viewModel,
                                navController = navController,
                                movie = movie,
                                path = movie.backdrop_path
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(viewModel: MainViewModel, navController: NavHostController, movie: TmdbMovie, path: String?) {
    Card(modifier = Modifier.padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .clickable { navController.navigate("MovieDetails/${movie.id}") },
        ) {
            Box (modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500$path",
                    contentDescription = "Movie Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                FavoriteMovieIcon(viewModel = viewModel, movie = movie)
            }
            Text(text = movie.title, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = movie.release_date, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FavoriteMovieIcon(viewModel: MainViewModel, movie: TmdbMovie) {
    IconButton(onClick = {
        if(movie.isFav) {
            viewModel.deleteFavMovie(movie.id)
            if(viewModel.isFavList) {
                viewModel.getFavMovies()
            } else {
                viewModel.getTrendingMovies()
            }
        } else {
            viewModel.addFavMovie(MovieEntity(content = movie, id = movie.id))
            if(viewModel.isFavList) {
                viewModel.getFavMovies()
            } else {
                viewModel.getTrendingMovies()
            }
        }
    }) {
        Icon(imageVector = Icons.Outlined.Favorite,
            contentDescription = "Favorite Icon",
            tint = if (movie.isFav) Color.Red else Color.White,
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp))
    }
}