package com.example.aventurape_androidmobile.navigation

import AccountInformationA
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
import com.example.aventurape_androidmobile.userProfileManagement.screens.AccountAdventurer
import com.example.aventurape_androidmobile.userProfileManagement.screens.AccountInformationE
import com.example.aventurape_androidmobile.userProfileManagement.screens.BestAdventures
import com.example.aventurape_androidmobile.userProfileManagement.screens.states.ProfileViewModelA
import com.example.aventurape_androidmobile.userProfileManagement.screens.states.ProfileViewModelE

@Composable
fun AdventurerNavigation(navController: NavHostController) {
    val signUpViewModel: SignUpViewModel = viewModel()
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
        composable(NavScreenAdventurer.HomeScreenA.name) {
            //HomeScreenAdventurer()
        }
        composable(NavScreenAdventurer.SearchA.name) {
            //SearchAdventurer()
        }
        composable(NavScreenAdventurer.AccountA.name) {
            AccountAdventurer(navController)
        }
        composable(NavScreenAdventurer.BestAdventuresA.name ) {
            BestAdventures()
        }
        composable(NavScreenAdventurer.AccountInformationA.name) {
            val viewModelA: ProfileViewModelA = viewModel()
            AccountInformationA(viewModelA = viewModelA, navController = navController)
        }
        composable(NavScreenAdventurer.AccountInformationE.name) {
            val viewModelE: ProfileViewModelE = viewModel()
            AccountInformationE(viewModelE = viewModelE)
        }
    }
}