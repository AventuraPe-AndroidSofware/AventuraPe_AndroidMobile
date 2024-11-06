package com.example.aventurape_androidmobile.utils.models


data class PublicationByOrderResponse(
    val Id: Long,
    val entrepreneurId: Long,
    val nameActivity: String,
    val description: String,
    val timeDuration: Int,
    val image: String,
    val cantPeople: Int,
    val cost: Double,
    val averageRating: Float
)