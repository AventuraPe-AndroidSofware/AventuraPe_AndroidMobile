package com.example.aventurape_androidmobile.domains.adventurer.viewModels

import com.example.aventurape_androidmobile.domains.adventurer.models.Review
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun sendReview(review: Review, publicationId: Long, onSuccess: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitClient.placeholder.sendReview(publicationId, review)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    println("Failed to send review: ${response.errorBody()?.string()}")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                println("Exception occurred: ${e.message}")
            }
        }
    }
}