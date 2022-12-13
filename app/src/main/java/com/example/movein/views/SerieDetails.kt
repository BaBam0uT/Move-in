package com.example.movein.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Text
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
import com.example.movein.models.TmdbSerie
import com.example.movein.viewmodel.MainViewModel

@Composable
fun SerieDetails(
    classes: WindowSizeClass,
    viewModel: MainViewModel,
    idSerie: String?
) {
    val serieDetails = viewModel.serie.collectAsState()
    if (idSerie != null) {
        viewModel.getSerieDetails(idSerie)
    }
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(3) }
    when (classes.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier
                    .background(Color.Black)) {
                item(span = span) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + serieDetails.value.backdrop_path,
                        contentDescription = "Backdrop Image",
                        modifier = Modifier
                            .padding(bottom = 20.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                item(span = span) {
                    Title(detailsSerie = serieDetails)
                }
                item(span = span) {
                    Overview(detailsSerie = serieDetails)
                }
                item(span = span) {
                    ReleaseDate(detailsSerie = serieDetails)
                }
                item(span = span) {
                    Genres(detailsSerie = serieDetails)
                }
                item(span = span) {
                    Headliners()
                }
                items(serieDetails.value.credits.cast) { credit ->
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
                        model = "https://image.tmdb.org/t/p/w500" + serieDetails.value.backdrop_path,
                        contentDescription = "Backdrop Image",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .height(200.dp)
                    )
                }
                item(span = span) {
                    Title(detailsSerie = serieDetails)
                }
                item(span = span) {
                    Overview(detailsSerie = serieDetails)
                }
                item(span = span) {
                    ReleaseDate(detailsSerie = serieDetails)
                }
                item(span = span) {
                    Genres(detailsSerie = serieDetails)
                }
                item(span = span) {
                    Headliners()
                }
                items(serieDetails.value.credits.cast) { credit ->
                    HeadlinersList(credit = credit)
                }
            }
        }
    }
}

// Displays the serie title
@Composable
fun Title(detailsSerie: State<TmdbSerie>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(bottom = 20.dp)) {
        Text(text = detailsSerie.value.name,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
            .background(Color.White)
            .padding(10.dp),
            fontWeight = FontWeight.Bold)

    }
}

// Displays the serie overview
@Composable
fun Overview(detailsSerie: State<TmdbSerie>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.White)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.poster_path,
            contentDescription = "Poster Image",
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        )
        Spacer(Modifier.height(10.dp))
        Column {
            Text(
                text = "Overview",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = detailsSerie.value.overview,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

    }
}

// Displays the serie release date
@Composable
fun ReleaseDate(detailsSerie: State<TmdbSerie>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(top = 30.dp, bottom = 30.dp)) {
        Text(
            text = "Released on " + detailsSerie.value.first_air_date,
            fontSize = 14.sp,
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        )
    }
}

// Displays the serie genres
@Composable
fun Genres(detailsSerie: State<TmdbSerie>) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        detailsSerie.value.genres.forEach { genre ->
            Text(text= genre.name, fontSize = 14.sp, modifier = Modifier
                .background(Color.White)
                .padding(10.dp))
        }
    }
}