package com.example.aventurape_androidmobile.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.aventurape_androidmobile.models.Items_bottom_navA.*
import com.example.aventurape_androidmobile.navigation.currentRoute

@Composable
fun BottomNavigationAdventurer(
    navController: NavHostController
) {
    val menu_items= listOf(
        Item_bottom_nav1,
        Item_bottom_nav2,
        Item_bottom_nav3
    )
    BottomAppBar {
        NavigationBar (
            containerColor = MaterialTheme.colorScheme.inverseSurface
        ){
            menu_items.forEach{ item ->
                val selected = currentRoute(navController)==item.ruta
                NavigationBarItem(
                    selected = selected,
                    onClick = { navController.navigate(item.ruta) },
                    icon = {
                        Icon(imageVector=item.icon,
                            contentDescription = item.title)
                }
                )
            }
        }
    }
}