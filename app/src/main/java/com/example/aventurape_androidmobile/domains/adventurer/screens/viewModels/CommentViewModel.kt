package com.example.aventurape_androidmobile.domains.adventurer.screens.viewModels

import com.example.aventurape_androidmobile.domains.adventurer.models.Comment
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getComments(publicationId: Long): List<Comment> {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.placeholder.getComments(publicationId)
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