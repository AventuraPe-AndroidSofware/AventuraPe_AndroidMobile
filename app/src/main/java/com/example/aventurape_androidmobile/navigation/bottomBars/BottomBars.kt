package com.example.aventurape_androidmobile.navigation.bottomBars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationAdventurer(navController: NavController) {
    NavigationBar (
        containerColor = Color(0x4BF0CCAA),
    )
    {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color(0xFF765532)) },
            label = { Text("Home", color = Color(0xFF765532)) },
            selected = false,
            onClick = { /* Navegar a Home */
                navController.navigate("home_adventurer_screen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF765532))},
            label = { Text("Search", color = Color(0xFF765532)) },
            selected = false,
            onClick = { /* Navegar a AdventureScreen */
                navController.navigate("adventure_screen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color(0xFF765532))},
            label = { Text("Profile", color = Color(0xFF765532)) },
            selected = false,
            onClick = { /* Navegar a Profile */
                navController.navigate("account_adventurer_screen")
            }
        )
    }
}

@Composable
fun BottomNavigationBusiness(navController: NavController) {
    NavigationBar (
        containerColor = Color(0x4BF0CCAA),
    ){
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Home", tint = Color(0xFF765532)) },
            label = { Text("Dashboard") },
            selected = false,
            onClick = { /* Navegar a Dashboard */
                navController.navigate("adventure_publication_management")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Statistics", tint = Color(0xFF765532)) },
            label = { Text("Statistics") },
            selected = false,
            onClick = { /* Navegar a Orders */
                navController.navigate("statics_entrepreneur_screen")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color(0xFF765532)) },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* Navegar a Settings */
                navController.navigate("account_entrepreneur_screen")
            }
        )
    }
}