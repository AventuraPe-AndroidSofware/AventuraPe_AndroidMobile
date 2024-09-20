package com.example.aventurape_androidmobile.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aventurape_androidmobile.domains.authentication.screens.LogInScreen
import com.example.aventurape_androidmobile.domains.authentication.screens.SelectRoleScreen
import com.example.aventurape_androidmobile.domains.authentication.screens.SignUpScreen
import com.example.aventurape_androidmobile.domains.authentication.screens.WelcomeScreen
import com.example.aventurape_androidmobile.domains.authentication.screens.states.LoginViewModel
import com.example.aventurape_androidmobile.domains.authentication.screens.states.SignUpViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val signUpViewModel: SignUpViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavScreen.welcome_screen.name
    ) {
        composable(NavScreen.welcome_screen.name) {
            WelcomeScreen(navController = navController)
        }
        composable(NavScreen.login_screen.name) {
            val viewModel: LoginViewModel = viewModel()
            LogInScreen(viewModel = viewModel, navController = navController)
        }
        composable(NavScreen.select_role_screen.name){
            SelectRoleScreen(viewModel = signUpViewModel, navController = navController)
        }
        composable(NavScreen.signup_screen.name){
            SignUpScreen(viewModel = signUpViewModel, navController = navController)
        }
    }
}