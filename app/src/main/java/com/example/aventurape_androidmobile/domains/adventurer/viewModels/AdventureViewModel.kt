package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.UserRolesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdventureViewModel : ViewModel() {

    var listaAdventures: ArrayList<Adventure> by mutableStateOf(arrayListOf())
    var users: List<UserRolesResponse> by mutableStateOf(listOf())

    fun getAdventures() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.placeholder.getAllAdventures()
            withContext(Dispatchers.Main) {
                if (response.body() != null) {
                    listaAdventures = response.body() as ArrayList<Adventure>
                }
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.placeholder.getUsers()
            withContext(Dispatchers.Main) {
                if (response.body() != null) {
                    users = response.body()!!
                }
            }
        }
    }

    fun getUsernameByAdventureId(adventureId: Long): String? {
        return users.find { it.id == adventureId }?.username
    }
}