package com.example.aventurape_androidmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aventurape_androidmobile.navigation.AdventurerNavigation
import com.example.aventurape_androidmobile.navigation.bottomBars.BottomNavigationAdventurer
import com.example.aventurape_androidmobile.navigation.bottomBars.BottomNavigationBusiness
import com.example.aventurape_androidmobile.ui.theme.AventuraPe_AndroidMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AventuraPe_AndroidMobileTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val userRoles = PreferenceManager.getUserRoles(context)
    val isAdventurer = userRoles?.contains("ROLE_ADVENTUROUS") == true
    val isBusiness = userRoles?.contains("ROLE_ENTREPRENEUR") == true

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    val shouldShowBottomBar = when (currentDestination) {
        "login_screen",
        "register_screen",
        "welcome_screen",
        "select_role_screen",
        "signup_screen",
        "error_screen" -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                when {
                    isAdventurer -> BottomNavigationAdventurer(navController)
                    isBusiness -> BottomNavigationBusiness(navController)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            AdventurerNavigation(navController = navController, context = context)
        }
    }
}