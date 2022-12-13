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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movein.entity.SerieEntity
import com.example.movein.models.TmdbSerie
import com.example.movein.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Series(
    windowSizeClass: WindowSizeClass,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val series by viewModel.series.collectAsState()
    viewModel.getTrendingSeries()
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "a TV serie",
                        onTextChange = { viewModel.updateSearchTextState(newValue = it) },
                        onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                        onSearchClicked = { viewModel.getSearchedSeries() },
                        onSearchTriggered = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                        viewModel = viewModel
                    )
                },
                bottomBar = {
                    NavigationBar(viewModel = viewModel, navController = navController)
                }
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                    modifier = Modifier.background(Color.Black)
                        .fillMaxSize()) {
                    items(series) { serie ->
                        SerieCard(viewModel = viewModel,
                            navController = navController,
                            serie = serie,
                            path = serie.poster_path
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
                        type = "a TV serie",
                        onTextChange = { viewModel.updateSearchTextState(newValue = it) },
                        onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                        onSearchClicked = { viewModel.getSearchedSeries() },
                        onSearchTriggered = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                        viewModel = viewModel
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(3),
                        modifier = Modifier.background(Color.Black)
                            .fillMaxSize()) {
                        items(series) { serie ->
                            SerieCard(viewModel = viewModel,
                                navController = navController,
                                serie = serie,
                                path = serie.backdrop_path
                            )
                        }
                    }
                }
            }
        }
    }
}

// Displays a serie card
@Composable
fun SerieCard(viewModel: MainViewModel, navController: NavHostController, serie: TmdbSerie, path: String?) {
    Card(modifier = Modifier.padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .clickable { navController.navigate("SerieDetails/${serie.id}") },
        ) {
            Box (modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500$path",
                    contentDescription = "Serie Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                FavoriteSerieIcon(viewModel = viewModel, serie = serie)
            }
            Text(text = serie.name, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = serie.first_air_date, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

// Displays a favorite icon for series
@Composable
fun FavoriteSerieIcon(viewModel: MainViewModel, serie: TmdbSerie) {
    IconButton(onClick = {
        if(serie.isFav) {
            viewModel.deleteFavSerie(serie.id)
            // Regenerates the list depending on whether it is a trending list or a favorite list
            if(viewModel.isFavList) {
                viewModel.getFavSeries()
            } else {
                viewModel.getTrendingSeries()
            }
        } else {
            viewModel.addFavSerie(SerieEntity(content = serie, id = serie.id))
            if(viewModel.isFavList) {
                viewModel.getFavSeries()
            } else {
                viewModel.getTrendingSeries()
            }
        }
    }) {
        Icon(imageVector = Icons.Outlined.Favorite,
            contentDescription = "Favorite Icon",
            tint = if (serie.isFav) Color.Red else Color.White,
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp))
    }
}