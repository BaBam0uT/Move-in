package com.example.movein

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movein.ui.theme.MoveInTheme
import com.example.movein.viewmodel.MainViewModel
import com.example.movein.views.*
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveInTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val navController = rememberNavController()
                val viewModel: MainViewModel by viewModels()
                NavHost(navController = navController, startDestination = "profile") {
                    composable("profile") { Screen(windowSizeClass, navController) }
                    composable("movies") { Movies(windowSizeClass, viewModel, navController) }
                    composable("series") { Series(windowSizeClass, viewModel, navController) }
                    composable("actors") { Actors(windowSizeClass, viewModel, navController) }
                    composable("MovieDetails/{idMovie}") { backStackEntry -> DetailsFilm(windowSizeClass, viewModel, backStackEntry.arguments?.getString("idMovie")) }
                    composable("SerieDetails/{idSerie}") { backStackEntry -> DetailsSerie(windowSizeClass, viewModel, backStackEntry.arguments?.getString("idSerie")) }
                }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MonProfilTheme {
        Screen()
    }
}*/