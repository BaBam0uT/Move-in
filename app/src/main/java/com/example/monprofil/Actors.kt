package com.example.monprofil

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.monprofil.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Actors(
    windowSizeClass: WindowSizeClass,
    viewmodel: MainViewModel,
    navController: NavHostController
) {
    val classeLargeur = windowSizeClass.widthSizeClass
    val actors by viewmodel.actors.collectAsState()
    val items = listOf(
        Screen("films", painterResource(id = R.drawable.movies), "Icone Films", "Films") ,
        Screen("series", painterResource(id = R.drawable.series), "Icone Series", "Séries"),
        Screen("actors", painterResource(id = R.drawable.actors), "Icone Acteurs", "Acteurs")
    )
    val searchWidgetState by viewmodel.searchWidgetState
    val searchTextState by viewmodel.searchTextState
    if (viewmodel.searchTextState.value == "") {
        viewmodel.getActorsInitiaux()
    } else {
        viewmodel.getSearchActors()
    }
    when (classeLargeur) {
        WindowWidthSizeClass.Compact-> {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "un acteur",
                        onTextChange = {
                            viewmodel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            Log.d("Searched Text", it)
                        },
                        onSearchTriggered = {
                            viewmodel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }
                    )
                },
                bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.resourceId, contentDescription = screen.description) },
                                label = { Text(screen.label) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
                }
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                    modifier = Modifier.background(Color.Black)) {
                    items(actors) { actor ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(20.dp)
                                .background(Color.White)
                                .padding(10.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500" + actor.profile_path,
                                contentDescription = "Affiche du film"
                            )
                            Text(text = actor.name)
                        }
                    }
                }
            }
        }
        else -> {

        }
    }
}