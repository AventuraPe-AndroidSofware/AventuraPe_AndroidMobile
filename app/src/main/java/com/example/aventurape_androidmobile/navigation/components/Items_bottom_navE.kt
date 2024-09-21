package com.example.aventurape_androidmobile.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.aventurape_androidmobile.navigation.NavScreenAdventurer

sealed class Items_bottom_navE (
    val icon: ImageVector,
    val title: String,
    val ruta: String
){
    object Item_bottom_nav1:Items_bottom_navE(
        Icons.Outlined.Home,
        "Home",
        NavScreenAdventurer.HomeScreenE.name
    )
    object Item_bottom_nav2:Items_bottom_navE(
        Icons.Outlined.Build,
        "Stats",
        NavScreenAdventurer.StatsE.name
    )
    object Item_bottom_nav3:Items_bottom_navE(
        Icons.Outlined.Email,
        "Account",
        NavScreenAdventurer.AccountE.name
    )
}