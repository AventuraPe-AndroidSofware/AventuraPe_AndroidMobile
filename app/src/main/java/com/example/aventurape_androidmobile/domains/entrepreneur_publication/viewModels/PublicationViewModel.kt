package com.example.aventurape_androidmobile.domains.entrepreneur_publication.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.RetrofitClient.placeholder
import com.example.aventurape_androidmobile.utils.models.PublicationRequest
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PublicationViewModel : ViewModel() {

    var publications: MutableState<List<PublicationResponse>> = mutableStateOf(emptyList())

    // Create publications
    fun sendPublication(
        publicationRequest: PublicationRequest,
        entrepreneurId: Long,
        onSuccess: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.placeholder
                    .sendPublication(
                        publicationRequest
                    )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getPublications(entrepreneurId)
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

    // Eliminar publicación
    fun deletePublication(
        publicationId: Long,
        entrepreneurId: Long,
        onSuccess: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.placeholder
                    .deletePublication(
                        publicationId
                    )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getPublications(entrepreneurId)
                        onSuccess()
                    } else {
                        println("Failed to delete publication: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println("Failed to delete publication: ${e.message}")
                }
            }
        }
    }

    // List of publications
    fun getPublications(entrepreneurId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.placeholder
                    .getPublications( entrepreneurId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        publications.value = response.body() ?: emptyList()
                    } else {
                        publications.value = emptyList()
                    }
                }
            } catch (e: Exception) {
                publications.value = emptyList()
            }
        }
    }

    fun updatePublication(publicationRequest: PublicationRequest, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val response = placeholder.updatePublication(publicationRequest.Id, publicationRequest)
            onResult(response.isSuccessful)
        }
    }


}