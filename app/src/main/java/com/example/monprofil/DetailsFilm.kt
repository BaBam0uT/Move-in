package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.monprofil.viewmodels.MainViewModel

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
                modifier = Modifier.height(300.dp)
                    .background(Color.Black)
            ) {
                AsyncImage(model = "https://image.tmdb.org/t/p/w500" + detailsFilm.value.backdrop_path, contentDescription = "", modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(25.dp))
                Text(text = detailsFilm.value.title, fontSize = 20.sp, modifier = Modifier.background(Color.White))
                Spacer(Modifier.height(50.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    AsyncImage(model = "https://image.tmdb.org/t/p/w500" + detailsFilm.value.poster_path, contentDescription = "", modifier = Modifier.fillMaxHeight())
                    Spacer(Modifier.height(10.dp))
                    Text(text = detailsFilm.value.overview,
                        fontSize = 20.sp,
                        color = Color.Black)
                }
            }
        }
        else -> {
            Text(text = "PAGE DESCRIPTION FILM")
        }
    }
}