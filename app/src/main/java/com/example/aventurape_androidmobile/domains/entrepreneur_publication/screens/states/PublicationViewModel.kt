package com.example.aventurape_androidmobile.domains.entrepreneur_publication.screens.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PublicationViewModel : ViewModel() {

    // Create publications
    fun sendPublication(
        publicationRequest: PublicationRequest,
        token: String,
        entrepreneurId: Long,
        onSuccess: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.placeholder
                    .sendPublication(
                        "Bearer $token", entrepreneurId,
                        publicationRequest
                    )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        println("Failed to send publication: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println("Failed to send publication: ${e.message}")
                }
            }
        }
    }

    // List of publications
    suspend fun getPublications(token: String, entrepreneurId: Long): List<PublicationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.placeholder
                    .getPublications("Bearer $token", entrepreneurId)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}