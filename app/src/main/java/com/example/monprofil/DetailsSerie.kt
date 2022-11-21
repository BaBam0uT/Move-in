package com.example.monprofil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monprofil.viewmodels.MainViewModel

@Composable
fun DetailsSerie(
    classes: WindowSizeClass,
    viewmodel: MainViewModel,
    idSerie: String?
) {
    val classeLargeur = classes.widthSizeClass
    val detailsSerie = viewmodel.serie.collectAsState()
    if (idSerie != null) {
        viewmodel.getSeriesDetails(idSerie)
    }
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    when (classeLargeur) {
        WindowWidthSizeClass.Compact-> {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier
                    .background(Color.Black)) {
                item(span = span) {
                    AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.backdrop_path,
                            contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                            .padding(bottom = 20.dp),
                        contentScale = ContentScale.Crop
                        )
                }
                item(span = span) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(Color.Black)
                        .padding(bottom = 20.dp)) {
                        Text(text = detailsSerie.value.name, fontSize = 20.sp, modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp),
                            fontWeight = FontWeight.Bold)

                    }
                }
                item(span = span) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.background(Color.White)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.poster_path,
                            contentDescription = "",
                            modifier = Modifier
                                .background(Color.White)
                                .padding(10.dp)
                        )
                        Spacer(Modifier.height(10.dp))
                        Column() {
                            Text(
                                text = "Synopsis",
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
                item(span = span) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(Color.Black)
                        .padding(top = 20.dp, bottom = 20.dp)) {
                        Text(
                            text = "Sortie le : " + detailsSerie.value.first_air_date,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .background(Color.White)
                                .padding(10.dp)
                        )
                    }
                }
                item(span = span) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            detailsSerie.value.genres.forEach { genre ->
                                Text(text= genre.name, fontSize = 14.sp, modifier = Modifier
                                    .background(Color.White)
                                    .padding(10.dp))
                            }
                        }
                }
                item(span = span) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(Color.Black)
                        .padding(bottom = 20.dp)) {
                        Text(text = "Têtes d'affiche", fontSize = 20.sp, modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp),
                            fontWeight = FontWeight.Bold)

                    }
                }
                items(detailsSerie.value.credits.cast) { credit ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(20.dp)
                            .background(Color.White)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500" + credit.profile_path,
                            contentDescription = "Affiche du film"
                        )
                        Text(text = credit.name)
                        Text(text = credit.character)
                    }
                }
            }
        }
        else -> {
            Text(text = "PAGE DESCRIPTION SERIE")
        }
    }
}