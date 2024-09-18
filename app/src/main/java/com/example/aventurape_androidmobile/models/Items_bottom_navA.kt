package com.example.aventurape_androidmobile.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer

sealed class Items_bottom_navA (
    val icon: ImageVector,
    val title: String,
    val ruta: String
    ){
    object Item_bottom_nav1:Items_bottom_navA(
        Icons.Outlined.Home,
        "Home",
        NavScreenAdventurer.HomeScreenA.name
    )
    object Item_bottom_nav2:Items_bottom_navA(
        Icons.Outlined.Search,
        "Search",
        NavScreenAdventurer.SearchA.name
    )
    object Item_bottom_nav3:Items_bottom_navA(
        Icons.Outlined.Person,
        "Account",
        NavScreenAdventurer.AccountA.name
    )
}