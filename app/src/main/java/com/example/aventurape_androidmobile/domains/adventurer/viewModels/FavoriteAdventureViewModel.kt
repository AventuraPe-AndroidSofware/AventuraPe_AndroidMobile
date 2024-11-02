package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.FavoritePublicationRequest
import com.example.aventurape_androidmobile.utils.models.FavoritePublicationResponse
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class FavoriteAdventureViewModel : ViewModel() {
    var favorites = mutableStateOf<List<Adventure>>(emptyList())

    fun loadFavoriteAdventures(profileId: Long) {
        viewModelScope.launch {
            try {
                val favoriteResponse = RetrofitClient.placeholder.getFavoritePublicationByProfileId(profileId)
                if (favoriteResponse.isSuccessful) {
                    val favoritePublications = favoriteResponse.body() ?: emptyList()
                    val adventures = favoritePublications.mapNotNull { favorite ->
                        val adventureResponse = RetrofitClient.placeholder.getAdventureById(favorite.publicationId)
                        if (adventureResponse.isSuccessful) {
                            adventureResponse.body()
                        } else {
                            null
                        }
                    }
                    favorites.value = adventures
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


    fun addFavoritePublication(publicationId: Long, onResult: (Response<FavoritePublicationResponse>?) -> Unit) {
        val request = FavoritePublicationRequest(publicationId)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.placeholder.addFavoritePublication(request)
                withContext(Dispatchers.Main) {
                    onResult(response)
                }
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }


}