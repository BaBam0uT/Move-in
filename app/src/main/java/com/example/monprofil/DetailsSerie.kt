package com.example.monprofil

import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
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
    when (classeLargeur) {
        WindowWidthSizeClass.Compact-> {
            Text(text = detailsSerie.value.name)
        }
        else -> {
            Text(text = "PAGE DESCRIPTION SERIE")
        }
    }
}