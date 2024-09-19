package com.example.aventurape_androidmobile.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aventurape_androidmobile.R

@Composable
fun AccountAdventurer(){
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // User avatar and email
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "User Avatar",
                modifier = Modifier.size(57.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "nombre usuario",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "usuario@ejemplo.com",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Personal Information
        ProfileMenuItem(
            icon = Icons.Default.Person,
            title = "Información personal",
            tint = Color.Black,
            onClick = {
                navController.navigate("accountInformation")
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Best Ratings
        ProfileMenuItem(
            icon = Icons.Default.Star,
            title = "Mejores calificaciones",
            tint = Color.Black,
            onClick = {
                navController.navigate("bestAdventures")
            }
        )

    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String, tint: Color = MaterialTheme.colorScheme.primary,onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint // Aplicar el color aquí
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Navigate"
        )
    }
}
@Composable
fun AccountAdventurerNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "accountAdventurer") {
        composable("accountAdventurer") { AccountAdventurer() }
        composable("accountInformation") { AccountInformation() }
        composable("bestAdventures") { BestAdventures() }
    }
}