package com.example.aventurape_androidmobile.utils.models

data class PublicationResponse(
    val Id: Long,
    val entrepreneurId: Long,
    val nameActivity: String,
    val description: String,
    val timeDuration: String,
    val image: String,
    val cantPeople: Int,
    val cost: Int,
)