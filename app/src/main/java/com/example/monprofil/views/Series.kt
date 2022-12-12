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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.monprofil.entity.SerieEntity
import com.example.monprofil.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Series(
    windowSizeClass: WindowSizeClass,
    viewmodel: MainViewModel,
    navController: NavHostController
) {
    val series by viewmodel.series.collectAsState()
    val items = listOf(
        Screen("movies", painterResource(id = R.drawable.movies), "Movies Icon", "Movies"),
        Screen("series", painterResource(id = R.drawable.series), "Series Icon", "Series"),
        Screen("actors", painterResource(id = R.drawable.actors), "Actors Icon", "Actors"
        )
    )
    val searchWidgetState by viewmodel.searchWidgetState
    val searchTextState by viewmodel.searchTextState
    viewmodel.getSeriesInitiaux()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "une série",
                        onTextChange = {
                            viewmodel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                            viewmodel.getSearchSeries()
                        },
                        onSearchTriggered = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        viewmodel = viewmodel
                    )
                },
                bottomBar = {
                    BottomNavigation ( backgroundColor = colorResource(R.color.purple_700) ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.resourceId, contentDescription = screen.description, tint = Color.White) },
                                label = { Text(screen.label, color = Color.White) },
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
                    modifier = Modifier.background(Color.Black)) {
                    items(series) { serie ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(5.dp)
                                .background(Color.White)
                                .padding(5.dp)
                                .clickable { navController.navigate("detailsSerie/${serie.id}") },
                        ) {
                            Box {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500" + serie.poster_path,
                                    contentDescription = "Affiche de la série"
                                )
                                IconButton(onClick = {
                                    if(serie.isFav) {
                                        viewmodel.deleteFavSerie(serie.id)
                                        if(viewmodel.isFavList) {
                                            viewmodel.getFavSeries()
                                        } else {
                                            viewmodel.getSeriesInitiaux()
                                        }
                                    } else {
                                        viewmodel.addFavSerie(SerieEntity(fiche = serie, id = serie.id))
                                        if(viewmodel.isFavList) {
                                            viewmodel.getFavSeries()
                                        } else {
                                            viewmodel.getSeriesInitiaux()
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
                            Text(text = serie.name, color = Color.Black)
                            Text(text = serie.first_air_date, color = Color.Black)
                        }
                    }
                }
            }
        }
        else -> {
            Row {
                NavigationRail ( backgroundColor = colorResource(R.color.purple_700) ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        NavigationRailItem(
                            icon = { Icon(screen.resourceId, contentDescription = screen.description) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            selectedContentColor = Color.White,
                            onClick = {
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
                        type = "une série",
                        onTextChange = {
                            viewmodel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                            viewmodel.getSearchSeries()
                        },
                        onSearchTriggered = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        viewmodel = viewmodel
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(3),
                        modifier = Modifier.background(Color.Black)) {
                        items(series) { serie ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .background(Color.White)
                                    .padding(10.dp)
                                    .clickable { navController.navigate("detailsSerie/${serie.id}") },
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500" + serie.backdrop_path,
                                    contentDescription = "Affiche de la série"
                                )
                                Text(text = serie.name)
                                Text(text = serie.first_air_date)
                            }
                        }
                    }
                }
            }
        }
    }
}