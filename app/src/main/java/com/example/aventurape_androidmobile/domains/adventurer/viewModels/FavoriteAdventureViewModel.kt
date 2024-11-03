package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class FavoriteAdventureViewModel : ViewModel() {
        private val _favorites = MutableStateFlow<List<Adventure>>(emptyList())
        val favorites: StateFlow<List<Adventure>> = _favorites

        fun loadFavoriteAdventures(profileId: Long) {
            viewModelScope.launch {
                try {
                    val favoriteResponse = RetrofitClient.placeholder.getFavoritePublicationByProfileId(profileId)
                    if (favoriteResponse.isSuccessful) {
                        val favoritePublications = favoriteResponse.body() ?: emptyList()
                        val favoritePublicationIds = favoritePublications.map { it.publicationId }
                        val allAdventuresResponse = RetrofitClient.placeholder.getAllAdventures()
                        if (allAdventuresResponse.isSuccessful) {
                            val allAdventures = allAdventuresResponse.body() ?: emptyList()
                            val favoriteAdventures = allAdventures.filter { it.Id in favoritePublicationIds }
                            _favorites.value = favoriteAdventures
                        } else {
                            _favorites.value = emptyList()
                        }
                    } else {
                        _favorites.value = emptyList()
                    }
                } catch (e: Exception) {
                    _favorites.value = emptyList()
                }
            }
        }

        fun deleteFavoritePublication(profileId: Long, publicationId: Long, onResult: (Response<Unit>?) -> Unit) {
            viewModelScope.launch {
                try {
                    val favoriteResponse = RetrofitClient.placeholder.getFavoritePublicationByProfileId(profileId)
                    if (favoriteResponse.isSuccessful) {
                        val favoritePublications = favoriteResponse.body() ?: emptyList()
                        val favoritePublication = favoritePublications.find { it.publicationId == publicationId }
                        if (favoritePublication != null) {
                            val response = RetrofitClient.placeholder.deleteFavoritePublication(favoritePublication.id)
                            withContext(Dispatchers.Main) {
                                onResult(response)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                onResult(null)
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onResult(null)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FavoriteAdventureViewModel", "Error al eliminar la publicación de favoritos", e)
                    withContext(Dispatchers.Main) {
                        onResult(null)
                    }
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