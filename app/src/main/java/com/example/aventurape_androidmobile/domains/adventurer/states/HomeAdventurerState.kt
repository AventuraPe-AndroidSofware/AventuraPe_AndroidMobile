package com.example.aventurape_androidmobile.domains.adventurer.states

import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.models.ProfileE

data class HomeAdventurerState(
    val activities: List<Adventure> = emptyList(),
    val entrepreneurs: List<ProfileE> = emptyList(), // Nueva lista de emprendedores
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)