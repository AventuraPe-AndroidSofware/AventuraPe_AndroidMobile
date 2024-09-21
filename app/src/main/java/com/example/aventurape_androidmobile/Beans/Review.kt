package com.example.aventurape_androidmobile.Beans

data class Review(
    val id: Long,
    val content: String,
    val rating: Int,
    val publicationId: Long,
   // val userId: Long // Nuevo campo para el ID del usuario

)