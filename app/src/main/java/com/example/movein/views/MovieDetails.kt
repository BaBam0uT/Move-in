package com.example.movein.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movein.models.Cast
import com.example.movein.models.TmdbMovie
import com.example.movein.viewmodel.MainViewModel

@Composable
fun DetailsFilm(
    classes: WindowSizeClass,
    viewModel: MainViewModel,
    idMovie: String?
) {
    val movieDetails = viewModel.movie.collectAsState()
    if (idMovie != null) {
        viewModel.getFilmsDetails(idMovie)
    }
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(3) }
    when (classes.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier
                    .background(Color.Black)) {
                item(span = span) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + movieDetails.value.backdrop_path,
                        contentDescription = "Backdrop Image",
                        modifier = Modifier
                            .padding(bottom = 20.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                item(span = span) {
                    Titre(detailsFilm = movieDetails)
                }
                item(span = span) {
                    Synopsis(detailsFilm = movieDetails)
                }
                item(span = span) {
                    ReleasedDate(detailsFilm = movieDetails)
                }
                item(span = span) {
                    Genres(detailsFilm = movieDetails)
                }
                item(span = span) {
                    Headliners()
                }
                items(movieDetails.value.credits.cast) { credit ->
                    HeadlinersList(credit = credit)
                }
            }
        }
        else -> {
            LazyVerticalGrid(columns = GridCells.Fixed(3),
                modifier = Modifier
                    .background(Color.Black)) {
                item(span = span) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + movieDetails.value.backdrop_path,
                        contentDescription = "Backdrop Image",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .height(200.dp)
                    )
                }
                item(span = span) {
                    Titre(detailsFilm = movieDetails)
                }
                item(span = span) {
                    Synopsis(detailsFilm = movieDetails)
                }
                item(span = span) {
                    ReleasedDate(detailsFilm = movieDetails)
                }
                item(span = span) {
                    Genres(detailsFilm = movieDetails)
                }
                item(span = span) {
                    Headliners()
                }
                items(movieDetails.value.credits.cast) { credit ->
                    HeadlinersList(credit = credit)
                }
            }
        }
    }
}

@Composable
fun Titre(detailsFilm: State<TmdbMovie>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(bottom = 20.dp)) {
        Text(text = detailsFilm.value.title, fontSize = 20.sp, modifier = Modifier
            .background(Color.White)
            .padding(10.dp),
            fontWeight = FontWeight.Bold)

    }
}

@Composable
fun Synopsis(detailsFilm: State<TmdbMovie>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.White)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + detailsFilm.value.poster_path,
            contentDescription = "Poster Image",
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        )
        Spacer(Modifier.height(10.dp))
        Column {
            Text(
                text = "Synopsis",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = detailsFilm.value.overview,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

    }
}

@Composable
fun ReleasedDate(detailsFilm: State<TmdbMovie>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(top = 30.dp, bottom = 30.dp)) {
        Text(
            text = "Released on " + detailsFilm.value.release_date,
            fontSize = 14.sp,
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        )
    }
}

@Composable
fun Genres(detailsFilm: State<TmdbMovie>) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        detailsFilm.value.genres.forEach { genre ->
            Text(text= genre.name, fontSize = 14.sp, modifier = Modifier
                .background(Color.White)
                .padding(10.dp))
        }
    }
}

@Composable
fun Headliners() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(bottom = 10.dp)) {
        Text(text = "Headliners", fontSize = 20.sp, modifier = Modifier
            .background(Color.White)
            .padding(10.dp),
            fontWeight = FontWeight.Bold)

    }
}

@Composable
fun HeadlinersList(credit: Cast) {
    Card(modifier = Modifier.padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
        ) {
            Box (modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${credit.profile_path}",
                    contentDescription = "Actor Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = credit.name, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = credit.character, color = Color.Black, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}