package com.example.aventurape_androidmobile.utils.models

data class PublicationRequest(
    val Id: Long,//This is the id of the publication

    val entrepreneurId: Long,
    val nameActivity: String,
    val description: String,
    val timeDuration: String,
    val image: String,
    val cantPeople: Int,
    val cost: Int,
)
