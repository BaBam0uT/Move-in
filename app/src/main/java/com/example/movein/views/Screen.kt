package com.example.movein.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movein.R

@Composable
fun Screen(classes: WindowSizeClass, navController: NavHostController) {
    when (classes.widthSizeClass) {
        WindowWidthSizeClass.Compact-> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Presentation()
                Informations()
                Spacer(Modifier.height(20.dp))
                StartButton { navController.navigate("movies") }
            }
        }
        else -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Presentation()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Informations()
                    Spacer(Modifier.height(20.dp))
                    StartButton { navController.navigate("movies") }
                }
            }
        }
    }
}

@Composable
fun Presentation() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.height(300.dp)
    ) {
        Image(
            painterResource(R.drawable.moi),
            contentDescription = "Profile Image",
            Modifier
                .clip(RoundedCornerShape(75.dp))
                .size(150.dp),
            contentScale = ContentScale.Crop
        )
        Text(text="Valentin DEDET",
            fontSize = 30.sp,
        )
        Text(text="Étudiant alternant en développement d'applications mobiles à la licence professionnelle DReAM à Castres",
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier.width(350.dp)
        )
    }
}

@Composable
fun Informations() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Information(icon = Icons.Rounded.Email, text = "valentin.dedet@hotmail.fr")
        Information(icon = Icons.Rounded.Share, text = "https://www.linkedin.com/in/valentin-dedet/")
    }
}

@Composable
fun Information(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = "Localized description")
        Spacer(Modifier.width(10.dp))
        Text(text = text,
        fontSize = 14.sp,)
    }
}

@Composable
fun StartButton(changerPageFilms: () -> Unit) {
    Button(onClick = changerPageFilms) {
        Text(text = "Start")
    }
}