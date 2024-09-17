package com.example.aventurape_androidmobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.aventurape_androidmobile.screens.AccountAdventurer
import com.example.aventurape_androidmobile.screens.HomeScreenAdventurer
import com.example.aventurape_androidmobile.screens.SearchAdventurer
import androidx.navigation.compose.composable

@Composable
fun AdventurerNavigation(
    navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreenAdventurer.HomeScreenA.name
    ) {
        composable(NavScreenAdventurer.HomeScreenA.name) {
            HomeScreenAdventurer()
        }
        composable(NavScreenAdventurer.SearchA.name) {
            SearchAdventurer()
        }
        composable(NavScreenAdventurer.AccountA.name) {
            AccountAdventurer()
        }
    }
}
