package com.example.aventurape_androidmobile.domains.adventurer.states

import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.entrepreneur_publication.models.ProfileE

data class AventurateState(
    val activities: List<Adventure> = emptyList(),
    val activitiesRandom: List<Adventure> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
