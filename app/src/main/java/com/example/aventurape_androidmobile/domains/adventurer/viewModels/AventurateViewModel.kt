package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.domains.adventurer.states.AventurateState
import com.example.aventurape_androidmobile.utils.RetrofitClient

class AventurateViewModel:ViewModel() {

    var state by mutableStateOf(AventurateState())
        private set

    suspend fun loadActivities() {
        state = state.copy(isLoading = true)
        try {
            val response = RetrofitClient.placeholder.getAllAdventures() // Assume API call setup
            if (response.isSuccessful) {
                val activities = response.body() ?: emptyList()
                state = state.copy(activities = activities, isLoading = false)
            } else {
                state = state.copy(errorMessage = "Failed to load activities", isLoading = false)
            }
        }
        catch (e: Exception) {
            state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
        }
    }

    fun loadRandomActivities() {
        if (state.activities.isEmpty()) {
            state = state.copy(errorMessage = "No activities available")
            return
        }
        val randomActivities = state.activities.shuffled().take(3) // Select 3 random activities
        state = state.copy(activitiesRandom = randomActivities)
    }
}