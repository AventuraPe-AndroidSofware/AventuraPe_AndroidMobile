package com.example.aventurape_androidmobile.domains.authentication.screens.states

import com.example.aventurape_androidmobile.Beans.Comment
import com.example.aventurape_androidmobile.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getComments(token: String, publicationId: Long): List<Comment> {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.placeholder.getComments("Bearer $token", publicationId)
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