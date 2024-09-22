// AdventureNavigation.kt
package com.example.aventurape_androidmobile.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aventurape_androidmobile.domains.authentication.screens.*
import com.example.aventurape_androidmobile.domains.authentication.screens.states.AdventureViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.states.LoginViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.states.SignUpViewModel
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.AppPublicationManagement

@Composable
fun AdventurerNavigation(navController: NavHostController) {
    val signUpViewModel: SignUpViewModel = viewModel()
    val adventureViewModel: AdventureViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavScreenAdventurer.welcome_screen.name
    ) {
        composable(NavScreenAdventurer.welcome_screen.name) {
            WelcomeScreen(navController = navController)
        }
        composable(NavScreenAdventurer.login_screen.name) {
            val viewModel: LoginViewModel = viewModel()
            LogInScreen(viewModel = viewModel, navController = navController)
        }
        composable(NavScreenAdventurer.select_role_screen.name){
            SelectRoleScreen(viewModel = signUpViewModel, navController = navController)
        }
        composable(NavScreenAdventurer.signup_screen.name){
            SignUpScreen(viewModel = signUpViewModel, navController = navController)
        }
        composable(NavScreenAdventurer.adventure_screen.name) {
            AdventureScreen(viewModel = adventureViewModel, navController = navController)
        }
        composable("detail_adventure/{adventureId}") { backStackEntry ->
            val adventureId = backStackEntry.arguments?.getLong("adventureId")
            val adventure = adventureViewModel.listaAdventures.find { it.Id == adventureId }
            adventure?.let { adventureDetail ->
                DetailView(navController = navController, adventure = adventureDetail)
            }
        }
        composable(NavScreenAdventurer.adventure_publication_management.name){
            AppPublicationManagement(navController = navController, entrepreneurId = 5)
        }
    }
}


