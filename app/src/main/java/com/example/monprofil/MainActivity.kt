package com.example.monprofil

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.monprofil.converter.Converters
import com.example.monprofil.database.AppDatabase
import com.example.monprofil.database.FilmDao
import com.example.monprofil.repository.FakeTmdbApi
import com.example.monprofil.repository.Repository
import com.example.monprofil.repository.TmdbAPI
import com.example.monprofil.ui.theme.MonProfilTheme
import com.example.monprofil.viewmodels.MainViewModel
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonProfilTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val navController = rememberNavController()
                val viewmodel: MainViewModel by viewModels()
                NavHost(navController = navController, startDestination = "profile") {
                    composable("profile") { Screen(windowSizeClass, navController) }
                    composable("films") { Films(windowSizeClass, viewmodel, navController) }
                    composable("series") { Series(windowSizeClass, viewmodel, navController) }
                    composable("actors") { Actors(windowSizeClass, viewmodel, navController) }
                    composable("detailsFilm/{idFilm}") { backStackEntry -> DetailsFilm(windowSizeClass, viewmodel, backStackEntry.arguments?.getString("idFilm")) }
                    composable("detailsSerie/{idSerie}") { backStackEntry -> DetailsSerie(windowSizeClass, viewmodel, backStackEntry.arguments?.getString("idSerie")) }
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