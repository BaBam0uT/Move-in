package com.example.movein.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import com.example.movein.entity.ActorEntity
import com.example.movein.models.TmdbActor
import com.example.movein.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Actors(
    windowSizeClass: WindowSizeClass,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val actors by viewModel.actors.collectAsState()
    viewModel.getTrendingActors()
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "an actor",
                        onTextChange = { viewModel.updateSearchTextState(newValue = it) },
                        onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                        onSearchClicked = { viewModel.getSearchedActors() },
                        onSearchTriggered = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                        viewModel = viewModel
                    )
                },
                bottomBar = {
                    NavigationBar(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()) {
                    items(actors) { actor ->
                        ActorCard(viewModel = viewModel,
                            actor = actor,
                            path = actor.profile_path
                        )
                    }
                }
            }
        }
        else -> {
            Row {
                NavigationRail(
                    viewModel = viewModel,
                    navController = navController
                )
                Column {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        type = "an actor",
                        onTextChange = { viewModel.updateSearchTextState(newValue = it) },
                        onCloseClicked = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                        onSearchClicked = { viewModel.getSearchedActors() },
                        onSearchTriggered = { viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                        viewModel = viewModel
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize()) {
                        items(actors) { actor ->
                            ActorCard(viewModel = viewModel,
                                actor = actor,
                                path = actor.profile_path
                            )
                        }
                    }
                }
            }
        }
    }
}

// Displays an actor card
@Composable
fun ActorCard(viewModel: MainViewModel, actor: TmdbActor, path: String?) {
    Card(modifier = Modifier.padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
        ) {
            Box (modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500$path",
                    contentDescription = "Actor Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                FavoriteActorIcon(viewModel = viewModel, actor = actor)
            }
            Text(text = actor.name, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

// Displays a favorite icon for actors
@Composable
fun FavoriteActorIcon(viewModel: MainViewModel, actor: TmdbActor) {
    IconButton(onClick = {
        if(actor.isFav) {
            viewModel.deleteFavActor(actor.id)
            // Regenerates the list depending on whether it is a trending list or a favorites list
            if(viewModel.isFavList) {
                viewModel.getFavActors()
            } else {
                viewModel.getTrendingActors()
            }
        } else {
            viewModel.addFavActor(ActorEntity(content = actor, id = actor.id))
            if(viewModel.isFavList) {
                viewModel.getFavActors()
            } else {
                viewModel.getTrendingActors()
            }
        }
    }) {
        Icon(imageVector = Icons.Outlined.Favorite,
            contentDescription = "Favorite Icon",
            tint = if (actor.isFav) Color.Red else Color.White,
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp))
    }
}