package com.example.aventurape_androidmobile.domains.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.navigation.NavScreen
import com.example.aventurape_androidmobile.ui.theme.cabinFamily
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen (navController: NavHostController){
    // Navegar a la pantalla de inicio de sesión después de 3 segundos
    LaunchedEffect(Unit) {
        delay(3000) // Esperar 3 segundos
        navController.navigate(NavScreen.login_screen.name) {
            popUpTo(NavScreen.welcome_screen.name) {
                inclusive = true
            }
        }
    }

    Column(
        modifier= Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_aventurape),
            contentDescription = "Logo image",
            modifier = Modifier.size(200.dp)
        )
        Text(text = "AventuraPe", fontFamily = cabinFamily, fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}