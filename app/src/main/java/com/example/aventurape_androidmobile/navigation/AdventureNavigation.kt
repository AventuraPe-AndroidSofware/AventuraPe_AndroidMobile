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
import com.example.aventurape_androidmobile.domains.authentication.screens.*
import com.example.aventurape_androidmobile.domains.authentication.screens.states.AdventureViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.states.LoginViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.states.SignUpViewModel
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.AppPublicationManagement

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

        composable(NavScreenAdventurer.adventure_screen.name) {
            if (userRole != null && userRole!!.isNotEmpty()) {
                when (userRole!![0]) {
                    Roles.ROLE_ADVENTUROUS.name -> {
                        AdventureScreen(viewModel = adventureViewModel, navController = navController)
                    }
                    else -> {
                        // Manejar otros roles o redirigir
                        //navController.navigate(NavScreenAdventurer.error_screen.name)
                    }
                }
            } else {
                // Manejar el caso de rol no válido
                //navController.navigate(NavScreenAdventurer.error_screen.name)
            }
        }
        composable(NavScreenAdventurer.adventure_publication_management.name) {
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

        // Pantalla de error en caso de rol incorrecto
        composable(NavScreenAdventurer.error_screen.name) {
            ErrorScreen(navController = navController)
        }
    }
}



