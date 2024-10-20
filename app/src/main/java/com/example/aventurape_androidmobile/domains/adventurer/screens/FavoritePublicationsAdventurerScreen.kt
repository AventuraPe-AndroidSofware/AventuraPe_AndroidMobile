package com.example.aventurape_androidmobile.domains.adventurer.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavoritePublicationsAdventurerScreen(navController: NavController) {
    Text(
        text = "Fav publications",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}