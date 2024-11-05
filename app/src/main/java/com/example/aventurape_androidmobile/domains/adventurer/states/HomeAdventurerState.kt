package com.example.aventurape_androidmobile.domains.adventurer.states

import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure

data class HomeAdventurerState(
    val activities: List<Adventure> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)