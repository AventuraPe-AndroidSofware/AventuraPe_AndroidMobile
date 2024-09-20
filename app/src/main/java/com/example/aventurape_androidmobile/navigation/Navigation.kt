package com.example.aventurape_androidmobile.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.aventurape_androidmobile.screens.AccountAdventurer
import com.example.aventurape_androidmobile.screens.HomeScreenAdventurer
import com.example.aventurape_androidmobile.screens.SearchAdventurer
import androidx.navigation.compose.composable
import com.example.aventurape_androidmobile.screens.AccountInformation
import com.example.aventurape_androidmobile.screens.BestAdventuresScreen

@Composable
fun Navigation(
    navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.HomeScreenA.name
    ) {
        composable(NavScreens.HomeScreenA.name) {
            HomeScreenAdventurer()
        }
        composable(NavScreens.SearchA.name) {
            SearchAdventurer()
        }
        composable(NavScreens.AccountA.name) {
            AccountAdventurer(navController)
        }
        composable(NavScreens.BestAdventuresA.name ) {
            BestAdventuresScreen()
        }
        composable(NavScreens.AccountInformation.name) {
            AccountInformation()
        }
        composable(NavScreens.PublicationDetailScreen.name) {
            navBackStackEntry -> val elementId=navBackStackEntry.arguments?.getString("elementId")
            if (elementId==null){
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            }
            else{
                //PublicationDetailScreen(elementId=elementId)
            }
        }
    }
}
