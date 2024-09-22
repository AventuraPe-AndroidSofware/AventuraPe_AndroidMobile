package com.example.aventurape_androidmobile.domains.adventurer.screens.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.utils.RetrofitClient

class AdventurerHomeViewModel:ViewModel() {
    private var state by mutableStateOf(AdventurerHomeState())

    private var adventures: List<Adventure> = emptyList()

    fun resetState() {
        state = AdventurerHomeState()
    }

    suspend fun listAdventures() {
        val response = RetrofitClient.placeholder.getAllAdventures()
        if (response.isSuccessful) {
            adventures = response.body()!!
            state = state.copy(adventures = adventures, isLoading = false, error = null)
        }else{
            state = state.copy(isLoading = false, error = "Error while fetching data: ${response.errorBody()}")
        }
    }




}