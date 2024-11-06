package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.adventurer.screens.ProfileMenuItem
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer

@Composable
fun AccountEntrepreneur(navController:NavController , username: String?){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
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
                    text = username?:"Username",
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
            title = "Información de la empresa",
            tint = Color.Black,
            onClick = {
                navController.navigate(NavScreenAdventurer.account_infomation_entrepreneur_screen.name)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        Button(
            onClick = {
                navController.navigate(NavScreenAdventurer.login_screen.name) {
                    popUpTo(NavScreenAdventurer.home_adventurer_screen.name) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF765532)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Cerrar sesión", color = Color.White)
        }

    }
}
