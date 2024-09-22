package com.example.aventurape_androidmobile.domains.adventurer.screens.states

import com.example.aventurape_androidmobile.Beans.Adventure

data class AdventurerHomeState (
    val adventures: List<Adventure> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = ""
)