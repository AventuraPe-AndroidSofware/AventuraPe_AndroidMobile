package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.states.HomeAdventurerState
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.launch

class HomeAdventurerViewModel : ViewModel() {

    var state by mutableStateOf(HomeAdventurerState())
        private set

    fun loadActivities() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val response = RetrofitClient.placeholder.getAllAdventures() // Assume API call setup
                if (response.isSuccessful) {
                    val activities = response.body() ?: emptyList()
                    state = state.copy(activities = activities, isLoading = false)
                } else {
                    state = state.copy(errorMessage = "Failed to load activities", isLoading = false)
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.localizedMessage, isLoading = false)
            }
        }
    }
}