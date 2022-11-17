package com.example.monprofil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.monprofil.viewmodels.MainViewModel
import okhttp3.internal.wait

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
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                item(span = span) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color.Black)
                    ) {
                        AsyncImage(model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.backdrop_path,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .border(10.dp, Color.White),
                                contentScale = ContentScale.FillBounds
                        )
                        Spacer(Modifier.height(20.dp))
                        Text(text = detailsSerie.value.original_name, fontSize = 20.sp, modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp),
                            fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.background(Color.White)
                        ) {
                            AsyncImage(model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.poster_path, contentDescription = "",
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
                                Text(text = detailsSerie.value.overview,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(10.dp))
                            }

                        }
                        Spacer(Modifier.height(20.dp))
                        Text(text= "Sortie le : " + detailsSerie.value.first_air_date, fontSize = 14.sp, modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp))
                        Spacer(Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            detailsSerie.value.genres.forEach { genre ->
                                Text(text= genre.name, fontSize = 14.sp, modifier = Modifier
                                    .background(Color.White)
                                    .padding(10.dp))
                            }
                        }
                    }
                }
            }
        }
        else -> {
            Text(text = "PAGE DESCRIPTION SERIE")
        }
    }
}