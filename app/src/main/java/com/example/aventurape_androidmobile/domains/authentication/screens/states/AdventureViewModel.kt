package com.example.aventurape_androidmobile.domains.authentication.screens.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class AdventureViewModel : ViewModel() {

    var listaAdventures: ArrayList<Adventure> by mutableStateOf(arrayListOf())

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
}