// AdventureNavigation.kt
package com.example.aventurape_androidmobile.navigation

import PreferenceManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aventurape_androidmobile.domains.adventurer.screens.AccountAdventurer
import com.example.aventurape_androidmobile.domains.adventurer.screens.AdventureScreen
import com.example.aventurape_androidmobile.domains.adventurer.screens.DetailView
import com.example.aventurape_androidmobile.domains.adventurer.screens.HomeAdventurer
import com.example.aventurape_androidmobile.domains.authentication.screens.*
import com.example.aventurape_androidmobile.domains.adventurer.screens.viewModels.AdventureViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.LoginViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.viewModels.SignUpViewModel
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.AccountEntrepreneur
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.AppPublicationManagement
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.StaticsEntrepreneurScreen
import com.example.aventurape_androidmobile.shared.screens.ErrorScreen

@Composable
fun AdventurerNavigation(navController: NavHostController, context: Context) {
    val signUpViewModel: SignUpViewModel = viewModel()
    val adventureViewModel: AdventureViewModel = viewModel()
    var userRole = PreferenceManager.getUserRoles(context);

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
        composable(NavScreenAdventurer.select_role_screen.name) {
            SelectRoleScreen(viewModel = signUpViewModel, navController = navController)
        }
        composable(NavScreenAdventurer.signup_screen.name) {
            SignUpScreen(viewModel = signUpViewModel, navController = navController)
        }

        //aventurero screens

        composable(NavScreenAdventurer.home_adventurer_screen.name) {
            if (userRole != null && userRole!!.contains(Roles.ROLE_ADVENTUROUS.name)) {
                HomeAdventurer()
            } else {
                // Handle unauthorized access or redirect
                navController.navigate(NavScreenAdventurer.error_screen.name)
            }
        }
        composable(NavScreenAdventurer.account_adventurer_screen.name) {
            if (userRole != null && userRole!!.contains(Roles.ROLE_ADVENTUROUS.name)) {
                AccountAdventurer()
            } else {
                // Handle unauthorized access or redirect
                navController.navigate(NavScreenAdventurer.error_screen.name)
            }
        }
        composable(NavScreenAdventurer.adventure_screen.name) {
            if (userRole != null && userRole!!.isNotEmpty()) {
                when (userRole!![0]) {
                    Roles.ROLE_ADVENTUROUS.name -> {
                        AdventureScreen(viewModel = adventureViewModel, navController = navController)
                    }
                    else -> {
                        // Handle other roles or redirect
                        navController.navigate(NavScreenAdventurer.error_screen.name)
                    }
                }
            } else {
                // Handle invalid role case
                navController.navigate(NavScreenAdventurer.error_screen.name)
            }
        }

        // Detalle de aventura
        composable("detail_adventure/{adventureId}") { backStackEntry ->
            val adventureId = backStackEntry.arguments?.getString("adventureId")?.toLongOrNull()
            Log.d("AventureId", "detail_adventure/${adventureId}")  // Agrega esta línea
            Log.d("List Adventures", adventureViewModel.listaAdventures.toString())
            val adventure = adventureViewModel.listaAdventures.find { it.Id == adventureId }
            adventure?.let { adventureDetail ->
                DetailView(navController = navController, adventure = adventureDetail)
            } ?: run {
                println("Adventure not found for ID: $adventureId")  // Agrega esta línea
                // Aquí puedes manejar el caso en que no se encuentra la aventura
            }
        }

//------enterpreneur screens
        composable(NavScreenAdventurer.adventure_publication_management.name) { //homescreen
            userRole = PreferenceManager.getUserRoles(context);
            if(userRole != null){
                if(userRole!![0] == Roles.ROLE_ENTREPRENEUR.name){
                    AppPublicationManagement(navController = navController, entrepreneurId = PreferenceManager.getUserId(context))
                }
                else{
                    // Mostrar pantalla de error o redirigir
                    //navController.navigate(NavScreenAdventurer.error_screen.name)
                }
            }
        }
        composable(NavScreenAdventurer.statics_entrepreneur_screen.name) {
            if (userRole != null && userRole!!.contains(Roles.ROLE_ENTREPRENEUR.name)) {
                StaticsEntrepreneurScreen()
            } else {
                // Handle unauthorized access or redirect
                navController.navigate(NavScreenAdventurer.error_screen.name)
            }
        }
        composable(NavScreenAdventurer.account_entrepreneur_screen.name) {
            if (userRole != null && userRole!!.contains(Roles.ROLE_ENTREPRENEUR.name)) {
                AccountEntrepreneur()
            } else {
                // Handle unauthorized access or redirect
                navController.navigate(NavScreenAdventurer.error_screen.name)
            }
        }

        // Pantalla de error en caso de rol incorrecto
        composable(NavScreenAdventurer.error_screen.name) {
            ErrorScreen(navController = navController)
        }
    }
}



