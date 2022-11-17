package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.monprofil.viewmodels.MainViewModel
import java.security.AllPermission

@Composable
fun DetailsFilm(
    classes: WindowSizeClass,
    viewmodel: MainViewModel,
    idFilm: String?
) {
    val classeLargeur = classes.widthSizeClass
    val detailsFilm = viewmodel.movie.collectAsState()
    if (idFilm != null) {
        viewmodel.getFilmsDetails(idFilm)
    }
    when (classeLargeur) {
        WindowWidthSizeClass.Compact-> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxHeight()
            ) {
                AsyncImage(model = "https://image.tmdb.org/t/p/w500" + detailsFilm.value.backdrop_path,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(30.dp))
                Text(text = detailsFilm.value.title, fontSize = 20.sp, modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp),
                fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.background(Color.White)
                ) {
                    AsyncImage(model = "https://image.tmdb.org/t/p/w500" + detailsFilm.value.poster_path, contentDescription = "",
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp))
                    Spacer(Modifier.height(10.dp))
                    Column() {
                        Text(text = "Synopsis",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            fontWeight = FontWeight.Bold)
                        Text(text = detailsFilm.value.overview,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(10.dp))
                    }

                }
                Spacer(Modifier.height(30.dp))
                Text(text= "Sortie le : " + detailsFilm.value.release_date, fontSize = 14.sp, modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp))
                Spacer(Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    detailsFilm.value.genres.forEach { genre ->
                        Text(text= genre.name, fontSize = 14.sp, modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp))
                    }
                }
            }
        }
        else -> {
            Text(text = "PAGE DESCRIPTION FILM")
        }
    }
}